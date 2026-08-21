package com.example.korkomat.lesson.service

import com.example.korkomat.auth.service.CurrentProfileService
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.lesson.dto.request.BookLessonRequest
import com.example.korkomat.lesson.dto.response.BookLessonResponse
import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.lesson.entity.enumeration.SlotStatus
import com.example.korkomat.lesson.excpeptions.AvailableSlotDoesNotExistException
import com.example.korkomat.lesson.excpeptions.InvalidSlotTimeException
import com.example.korkomat.lesson.excpeptions.SlotUnavailableException
import com.example.korkomat.lesson.excpeptions.TutorSubjectDoesNotExistException
import com.example.korkomat.lesson.repository.AvailableSlotRepository
import com.example.korkomat.lesson.repository.LessonRepository
import com.example.korkomat.lesson.repository.TutorSubjectRepository
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
    override fun bookLesson(
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
                String().format(
                    Constant.AVAILABLE_SLOT_ALREADY_STARTED, slotId
                )
            )
        }

        if (!bookingSlot.slotStatus.equals(SlotStatus.AVAILABLE)) {
            throw SlotUnavailableException(
                String.format(
                    Constant.UNAVAILABLE_SLOT, slotId
                )
            )
        }

        val hasOverlappingLesson = lessonRepository.existsOverlappingLesson(
            studentProfile = student,
            startTime = bookingSlot.startTime,
            endTime = bookingSlot.endTime,
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

        val lesson = Lesson(
            slot = bookingSlot,
            studentProfile = student,
            tutorSubject = tutorSubject,
            place = request.place,
            )

        lessonRepository.save(lesson)

        bookingSlot.slotStatus = SlotStatus.RESERVED

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
}