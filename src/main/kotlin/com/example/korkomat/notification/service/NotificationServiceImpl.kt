package com.example.korkomat.notification.service

import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.notification.email.service.NotificationEmailService
import com.example.korkomat.notification.entity.Notification
import com.example.korkomat.notification.entity.enumeration.NotificationStatus
import com.example.korkomat.notification.entity.enumeration.NotificationType
import com.example.korkomat.notification.repository.NotificationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class NotificationServiceImpl(
    private val notificationRepository: NotificationRepository,
) : NotificationService {

    @Transactional
    override fun notifyStudentLessonConfirmed(lesson: Lesson) {
        notificationRepository.save(
            Notification(
                user = lesson.studentProfile.user,
                lesson = lesson,
                type = NotificationType.LESSON_CONFIRMED,
                status = NotificationStatus.PENDING
            )
        )
    }

    @Transactional
    override fun notifyTutorLessonBooked(lesson: Lesson) {
        notificationRepository.save(
            Notification(
                user = lesson.slot.tutorProfile.user,
                lesson = lesson,
                type = NotificationType.LESSON_BOOKED,
                status = NotificationStatus.PENDING
            )
        )
    }

    @Transactional
    override fun notifyStudentLessonRejected(lesson: Lesson) {
        notificationRepository.save(
            Notification(
                user = lesson.studentProfile.user,
                lesson = lesson,
                type = NotificationType.LESSON_REJECTED,
                status = NotificationStatus.PENDING
            )
        )
    }
}