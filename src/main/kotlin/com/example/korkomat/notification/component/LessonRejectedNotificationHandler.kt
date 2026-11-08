package com.example.korkomat.notification.component

import com.example.korkomat.notification.NotificationDateUtil
import com.example.korkomat.notification.email.service.NotificationEmailService
import com.example.korkomat.notification.entity.Notification
import com.example.korkomat.notification.entity.enumeration.NotificationType
import org.springframework.stereotype.Component

@Component
class LessonRejectedNotificationHandler(
    private val emailService: NotificationEmailService
): NotificationHandler {
    override val type = NotificationType.LESSON_REJECTED

    override fun send(notification: Notification) {
        val lesson = requireNotNull(notification.lesson)

        val studentEmail = notification.user.email
        val tutorName = requireNotNull(
            lesson.slot.tutorProfile
        ).user.getFullName()

        val lessonSubject = requireNotNull(
            lesson.tutorSubject
        ).subject.name

        emailService.sendLessonRejected(
            email = studentEmail,
            lessonSubject = lessonSubject,
            lessonDate = NotificationDateUtil.formatDate(lesson.slot.startTime, lesson.slot.endTime),
            tutorName = tutorName,
        )
    }
}