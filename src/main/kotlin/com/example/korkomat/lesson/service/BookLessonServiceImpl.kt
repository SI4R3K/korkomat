package com.example.korkomat.lesson.service

import com.example.korkomat.auth.exceptions.ForbiddenAccessException
import com.example.korkomat.auth.service.CurrentProfileService
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.lesson.dto.request.BookLessonRequest
import com.example.korkomat.lesson.dto.response.BookLessonResponse
import com.example.korkomat.lesson.dto.response.LessonResponse
import com.example.korkomat.lesson.dto.response.StudentLessonResponse
import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.lesson.entity.enumeration.LessonStatus
import com.example.korkomat.lesson.exceptions.LessonDoesNotExistException
import com.example.korkomat.slot.exceptions.AvailableSlotDoesNotExistException
import com.example.korkomat.slot.exceptions.InvalidSlotTimeException
import com.example.korkomat.subject.excpeptions.TutorSubjectDoesNotExistException
import com.example.korkomat.slot.repository.AvailableSlotRepository
import com.example.korkomat.lesson.repository.LessonRepository
import com.example.korkomat.subject.repository.TutorSubjectRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class BookLessonServiceImpl(
    private val availableSlotRepository: AvailableSlotRepository,
    private val currentProfileService: CurrentProfileService,
    private val lessonRepository: LessonRepository,
    private val tutorSubjectRepository: TutorSubjectRepository,
) : BookLessonService {

    @Transactional
    override fun reserveLesson(
        slotId: Long,
        request: BookLessonRequest
    ): BookLessonResponse {
        val student = currentProfileService.getCurrentStudent()

        val bookingSlot = availableSlotRepository.findByIdOrNull(slotId)
            ?: throw AvailableSlotDoesNotExistException(
                String.format(
                    Constant.AVAILABLE_SLOT_NOT_FOUND, slotId
                )
            )

        if (bookingSlot.startTime <= Instant.now()) {
            throw InvalidSlotTimeException(
                String.format(
                    Constant.AVAILABLE_SLOT_ALREADY_STARTED, slotId
                )
            )
        }

        val hasOverlappingLesson = lessonRepository.existsOverlappingLesson(
            studentProfile = student,
            startTime = bookingSlot.startTime,
            endTime = bookingSlot.endTime,
            activeStatuses = listOf(
                LessonStatus.PENDING,
                LessonStatus.CONFIRMED
            )
        )

        if (hasOverlappingLesson) {
            throw InvalidSlotTimeException(
                String.format(
                    Constant.LESSON_OVERLAPPING,slotId
                )
            )
        }

        val tutorSubject = tutorSubjectRepository.findByIdOrNull(request.tutorSubjectId)
            ?: throw TutorSubjectDoesNotExistException(
                String.format(Constant.SUBJECT_NOT_FOUND, request.tutorSubjectId)
            )

        bookingSlot.reserve()

        val lesson = Lesson(
            slot = bookingSlot,
            studentProfile = student,
            tutorSubject = tutorSubject,
            place = request.place,
            )
        lessonRepository.save(lesson)

        return BookLessonResponse(
            message = "Slot reserved. Awaiting tutor confirmation.",
            subjectName = tutorSubject.subject.name,
            level = tutorSubject.level,
            startTime = bookingSlot.startTime,
            endTime = bookingSlot.endTime,
            tutorName = bookingSlot.tutorProfile?.user?.username,
            lessonStatus = lesson.status,
        )
    }

    @Transactional
    override fun cancelReservation(id: Long): StudentLessonResponse {
        val student = currentProfileService.getCurrentStudent()
        val lessonToBeCancelled = lessonRepository.findByIdOrNull(id)
            ?: throw LessonDoesNotExistException(
                "Lesson with id $id does not exist."
            )

        if (lessonToBeCancelled.studentProfile.id != student.id) {
            throw ForbiddenAccessException(
                "User does not own lesson with id $id"
            )
        }

        lessonToBeCancelled.slot.release()
        lessonRepository.delete(lessonToBeCancelled)

        return StudentLessonResponse(
            id = lessonToBeCancelled.id,
            status = lessonToBeCancelled.status,
            startTime = lessonToBeCancelled.slot.startTime,
            endTime = lessonToBeCancelled.slot.endTime,
            place = lessonToBeCancelled.place,
            subjectName = lessonToBeCancelled.tutorSubject?.subject?.name,
            tutorName = lessonToBeCancelled.slot.tutorProfile?.user?.getFullName(),
        )
    }
}