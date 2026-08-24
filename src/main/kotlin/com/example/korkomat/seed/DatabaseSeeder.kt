package com.example.korkomat.seed

import com.example.korkomat.auth.authorization.Role
import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.lesson.entity.enumeration.LessonStatus
import com.example.korkomat.lesson.repository.LessonRepository
import com.example.korkomat.seed.dto.SeedAvailableSlot
import com.example.korkomat.seed.dto.SeedLesson
import com.example.korkomat.seed.dto.SeedStudent
import com.example.korkomat.seed.dto.SeedSubject
import com.example.korkomat.seed.dto.SeedTutor
import com.example.korkomat.seed.dto.SeedTutorSubject
import com.example.korkomat.slot.entity.AvailableSlot
import com.example.korkomat.slot.entity.enumeration.SlotStatus
import com.example.korkomat.slot.repository.AvailableSlotRepository
import com.example.korkomat.subject.entity.Subject
import com.example.korkomat.subject.entity.TutorSubject
import com.example.korkomat.subject.repository.SubjectRepository
import com.example.korkomat.subject.repository.TutorSubjectRepository
import com.example.korkomat.user.entity.StudentProfile
import com.example.korkomat.user.entity.TutorProfile
import com.example.korkomat.user.entity.User
import com.example.korkomat.user.repository.StudentRepository
import com.example.korkomat.user.repository.TutorRepository
import com.example.korkomat.user.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DatabaseSeeder(
    private val seedDataLoader: SeedDataLoader,

    private val userRepository: UserRepository,
    private val studentProfileRepository: StudentRepository,
    private val tutorProfileRepository: TutorRepository,

    private val subjectRepository: SubjectRepository,
    private val tutorSubjectRepository: TutorSubjectRepository,

    private val availableSlotRepository: AvailableSlotRepository,
    private val lessonRepository: LessonRepository
) : CommandLineRunner {
    @Transactional
    override fun run(vararg args: String) {

        val seedData = seedDataLoader.load()

        seedSubjects(seedData.subjects)

        seedStudents(seedData.students)

        seedTutors(seedData.tutors)

        seedLessons(seedData.lessons)
    }

    private fun seedSubjects(
        subjects: List<SeedSubject>
    ) {
        subjects.forEach { seedSubject ->
            subjectRepository.save(
                Subject(
                    name = seedSubject.name
                )
            )
        }
    }

    private fun seedStudents(
        students: List<SeedStudent>
    ) {
        students.forEach { seedStudent ->

            val user = User(
                email = seedStudent.email,
                password = User.encryptPassword(
                    seedStudent.password
                ),
                firstName = seedStudent.firstName,
                lastName = seedStudent.lastName,
                role = Role.USER
            )

            val savedUser = userRepository.save(user)

            studentProfileRepository.save(
                StudentProfile(
                    user = savedUser
                )
            )
        }
    }

    private fun seedTutors(
        tutors: List<SeedTutor>
    ) {
        tutors.forEach { seedTutor ->

            val user = User(
                email = seedTutor.email,
                password = User.encryptPassword(
                    seedTutor.password
                ),
                firstName = seedTutor.firstName,
                lastName = seedTutor.lastName,
                role = Role.USER
            )
            val savedUser = userRepository.save(user)
            val tutorProfile = tutorProfileRepository.save(
                TutorProfile(
                    user = savedUser,
                    bio = seedTutor.bio,
                    hourlyRate = seedTutor.hourlyRate
                )
            )

            seedTutorSubjects(
                tutorProfile,
                seedTutor.subjects
            )

            seedAvailableSlots(
                tutorProfile,
                seedTutor.slots
            )
        }
    }

    private fun seedTutorSubjects(
        tutorProfile: TutorProfile,
        subjects: List<SeedTutorSubject>
    ) {
        subjects.forEach { seedTutorSubject ->

            val subject = subjectRepository
                .findByName(seedTutorSubject.name)
                ?: throw IllegalStateException(
                    "Subject ${seedTutorSubject.name} does not exist."
                )

            tutorSubjectRepository.save(
                TutorSubject(
                    tutor = tutorProfile,
                    subject = subject,
                    level = seedTutorSubject.level,
                    description = seedTutorSubject.description
                )
            )
        }
    }

    private fun seedAvailableSlots(
        tutorProfile: TutorProfile,
        slots: List<SeedAvailableSlot>
    ) {
        slots.forEach { seedSlot ->

            availableSlotRepository.save(
                AvailableSlot(
                    tutorProfile = tutorProfile,
                    slotStatus = SlotStatus.AVAILABLE,
                    type = seedSlot.type,
                    startTime = seedSlot.startTime,
                    endTime = seedSlot.endTime
                )
            )
        }
    }

    private fun seedLessons(
        lessons: List<SeedLesson>
    ) {
        lessons.forEach { seedLesson ->

            val studentProfile =
                studentProfileRepository
                    .findByUserEmail(seedLesson.studentEmail)
                    ?: throw IllegalStateException(
                        "Student ${seedLesson.studentEmail} not found."
                    )

            val tutorSubject =
                tutorSubjectRepository
                    .findByTutorUserEmailAndSubjectNameAndLevel(
                        tutorEmail = seedLesson.tutorEmail,
                        subjectName = seedLesson.subjectName,
                        level = seedLesson.level
                    )
                    ?: throw IllegalStateException(
                        "Tutor subject not found."
                    )

            val slot =
                availableSlotRepository
                    .findByTutorProfileUserEmailAndStartTimeAndEndTime(
                        tutorEmail = seedLesson.tutorEmail,
                        startTime = seedLesson.startTime,
                        endTime = seedLesson.endTime
                    )
                    ?: throw IllegalStateException(
                        "Slot not found."
                    )

            val lesson = Lesson(
                slot = slot,
                studentProfile = studentProfile,
                tutorSubject = tutorSubject,
                status = LessonStatus.PENDING,
                place = seedLesson.place
            )

            when (seedLesson.status) {

                LessonStatus.PENDING -> {
                    slot.reserve(lesson)
                }

                LessonStatus.CONFIRMED -> {
                    slot.reserve(lesson)
                    lesson.confirm()
                    slot.book(lesson)
                }

                LessonStatus.REJECTED -> {
                    slot.reserve(lesson)
                    lesson.reject()
                }

                else -> {}
            }

            lessonRepository.save(lesson)
        }
    }
}


