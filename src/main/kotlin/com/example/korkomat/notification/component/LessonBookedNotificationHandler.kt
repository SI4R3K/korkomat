package com.example.korkomat.notification.component

import com.example.korkomat.notification.NotificationDateUtil
import com.example.korkomat.notification.email.service.NotificationEmailService
import com.example.korkomat.notification.entity.Notification
import com.example.korkomat.notification.entity.enumeration.NotificationType
import org.springframework.stereotype.Component

@Component
class LessonBookedNotificationHandler(
    private val emailService: NotificationEmailService
): NotificationHandler {
    override val type = NotificationType.LESSON_BOOKED

    override fun send(notification: Notification) {
        val lesson = requireNotNull(notification.lesson)

        val tutorEmail = notification.user.email
        val studentName = requireNotNull(
            lesson.studentProfile
        ).user.getFullName()

        val lessonSubject = requireNotNull(
            lesson.tutorSubject
        ).subject.name

        emailService.sendLessonBooked(
            email = tutorEmail,
            lessonSubject = lessonSubject,
            lessonDate = NotificationDateUtil.formatDate(lesson.slot.startTime, lesson.slot.endTime),
            studentName = studentName,
        )
    }
}