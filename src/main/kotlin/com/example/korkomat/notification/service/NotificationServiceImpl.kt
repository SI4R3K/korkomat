package com.example.korkomat.notification.service

import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.notification.email.service.NotificationEmailService
import com.example.korkomat.notification.entity.Notification
import com.example.korkomat.notification.entity.enumeration.NotificationStatus
import com.example.korkomat.notification.entity.enumeration.NotificationType
import com.example.korkomat.notification.repository.NotificationRepository
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class NotificationServiceImpl(
    private val notificationRepository: NotificationRepository,
    private val emailService: NotificationEmailService
) : NotificationService {

    private val formatter = DateTimeFormatter
        .ofPattern("dd.MM.yyyy HH:mm")
        .withZone(ZoneId.of("Europe/Warsaw"))

    @Transactional
    override fun notifyLessonConfirmed(lesson: Lesson) {
        notificationRepository.save(
            Notification(
                user = lesson.studentProfile.user,
                lesson = lesson,
                type = NotificationType.LESSON_CONFIRMED,
                status = NotificationStatus.PENDING
            )
        )
    }

    override fun sendPendingNotifications() {

        val notifications = notificationRepository
            .findAllByStatus(NotificationStatus.PENDING)

        notifications.forEach { notification ->
            try {
                val email = notification.user.email
                val lessonSubject = notification.lesson?.tutorSubject?.subject?.name ?: ""
                val lessonDate =
                    "from ${formatter.format(notification.lesson?.slot?.startTime)} " +
                    "to ${formatter.format(notification.lesson?.slot?.endTime)}"
                val tutorName = notification.lesson?.slot?.tutorProfile?.user?.getFullName() ?: ""

                when (notification.type) {
                    NotificationType.LESSON_CONFIRMED ->
                        emailService.sendLessonConfirmed(
                            email = email,
                            lessonSubject = lessonSubject,
                            lessonDate = lessonDate,
                            tutorName = tutorName,
                        )
                    else -> {}
                }

                notification.markAsSent()
            } catch (e: Exception) {
                notification.markAsFailed(e.message)
            }

            notificationRepository.save(notification)
        }
    }
}