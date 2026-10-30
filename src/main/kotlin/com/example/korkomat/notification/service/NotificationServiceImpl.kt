package com.example.korkomat.notification.service

import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.notification.email.service.NotificationEmailService
import com.example.korkomat.notification.entity.Notification
import com.example.korkomat.notification.entity.enumeration.NotificationStatus
import com.example.korkomat.notification.entity.enumeration.NotificationType
import com.example.korkomat.notification.repository.NotificationRepository
import org.slf4j.LoggerFactory
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

    private val logger = LoggerFactory
        .getLogger(NotificationServiceImpl::class.java)

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

    @Transactional
    override fun sendPendingNotifications() {

        val notifications = notificationRepository
            .findAllByStatus(NotificationStatus.PENDING)

        logger.info(
            "Found {} pending notifications",
            notifications.size
        )

        notifications.forEach { notification ->
            try {
                logger.info(
                    "Processing notification id={}, type={}, status={}",
                    notification.id,
                    notification.type,
                    notification.status
                )

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

                    NotificationType.LESSON_REJECTED ->
                        emailService.sendLessonRejected(
                            email = email,
                            lessonSubject = lessonSubject,
                            lessonDate = lessonDate,
                            tutorName = tutorName,
                        )

                    else -> {
                        throw IllegalArgumentException(
                            "Unsupported notification type ${notification.type}"
                        )
                    }
                }

                notification.markAsSent()

                logger.info(
                    "Notification {} sent successfully to {}",
                    notification.id,
                    email
                )

            } catch (e: Exception) {
                logger.error(
                    "Failed to process notification id={}",
                    notification.id,
                    e
                )
                notification.markAsFailed(e.message)
            }

            notificationRepository.save(notification)
        }
    }
}