package com.example.korkomat.lesson.service

import com.example.korkomat.auth.exceptions.ForbiddenAccessException
import com.example.korkomat.auth.service.CurrentProfileService
import com.example.korkomat.lesson.dto.request.LessonFilterRequest
import com.example.korkomat.lesson.dto.response.GetLessonsResponse
import com.example.korkomat.lesson.dto.response.LessonResponse
import com.example.korkomat.lesson.entity.enumeration.LessonStatus
import com.example.korkomat.lesson.exceptions.InvalidLessonStatusException
import com.example.korkomat.lesson.exceptions.NoLessonsException
import com.example.korkomat.lesson.mapper.toStudentLessonResponse
import com.example.korkomat.lesson.mapper.toTutorLessonResponse
import com.example.korkomat.lesson.repository.LessonRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LessonServiceImpl(
    private val currentProfileService: CurrentProfileService,
    private val lessonRepository: LessonRepository,
) : LessonService {

    @Transactional(readOnly = true)
    override fun getLessonsForStudent(filter: LessonFilterRequest): GetLessonsResponse {
        val student = currentProfileService.getCurrentStudent()

        return GetLessonsResponse(
            lessons = lessonRepository
                .findAllByStatusAndStudentProfileId(filter.status, student.id)
                .ifEmpty {
                    throw NoLessonsException(
                        "Student ${student.id} has no lessons with status ${filter.status}."
                    )
                }
                .map {
                    it.toStudentLessonResponse()
                }
        )
    }

    @Transactional(readOnly = true)
    override fun getLessonsForTutor(filter: LessonFilterRequest): GetLessonsResponse {
        val tutor = currentProfileService.getCurrentTutor()

        return GetLessonsResponse(
            lessons = lessonRepository
                .findAllByStatusAndTutorProfileId(filter.status, tutor.id)
                .ifEmpty {
                    throw NoLessonsException(
                        "Tutor ${tutor.id} has no lessons with status ${filter.status}."
                    )
                }
                .map {
                    it.toTutorLessonResponse()
                }
        )
    }

    @Transactional
    override fun confirmLesson(lessonId: Long): LessonResponse {
        val tutor = currentProfileService.getCurrentTutor()

        val lesson = lessonRepository.findByIdOrNull(lessonId)
            ?: throw NoLessonsException(
                "Lesson with id ${lessonId} does not exist."
            )

        if (lesson.slot.tutorProfile?.id != tutor.id) {
            throw ForbiddenAccessException(
                "Tutor with ${tutor.id} does not own a selected lesson."
            )
        }

        lesson.confirm()
        lesson.slot.book(lesson)

        return lesson.toTutorLessonResponse()
    }

    @Transactional
    override fun rejectLesson(lessonId: Long): LessonResponse {
        val tutor = currentProfileService.getCurrentTutor()

        val lesson = lessonRepository.findByIdOrNull(lessonId)
            ?: throw NoLessonsException(
                "Lesson with id ${lessonId} does not exist."
            )

        if (lesson.slot.tutorProfile?.id != tutor.id) {
            throw ForbiddenAccessException(
                "Tutor with ${tutor.id} does not own a selected lesson."
            )
        }

        lesson.reject()
        lesson.slot.release()

        return lesson.toTutorLessonResponse()
    }
}