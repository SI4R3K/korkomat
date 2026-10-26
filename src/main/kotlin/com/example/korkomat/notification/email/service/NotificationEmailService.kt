package com.example.korkomat.notification.email.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import java.nio.charset.StandardCharsets

@Service
class NotificationEmailService(
    private val javaMailSender: JavaMailSender,
    private val templateEngine: SpringTemplateEngine,

    @Value("\${spring.mail.username}")
    private val from: String,
) {

    private val logger = LoggerFactory.getLogger(
        NotificationEmailService::class.java
    )

    fun sendLessonConfirmed(
        email: String,
        lessonSubject: String,
        lessonDate: String,
        tutorName: String,
    ) {
        sendEmail(
            email = email,
            subject = "Your lesson has been confirmed",
            title = "Lesson confirmed!",
            message = "Great news! Your tutor has confirmed your lesson.",
            lessonSubject = lessonSubject,
            lessonDate = lessonDate,
            personLabel = "Tutor",
            personName = tutorName,
            accentColor = "#16a34a",
            accentLight = "#f0fdf4",
            statusLabel = "CONFIRMED",
        )
    }

    fun sendLessonRejected(
        email: String,
        lessonSubject: String,
        lessonDate: String,
        tutorName: String,
    ) {
        sendEmail(
            email = email,
            subject = "Your lesson request was declined",
            title = "Lesson request declined",
            message = """
                Unfortunately, your tutor couldn't accept this lesson request.
                You can visit Korkomat to find another suitable time.
            """.trimIndent(),
            lessonSubject = lessonSubject,
            lessonDate = lessonDate,
            personLabel = "Tutor",
            personName = tutorName,
            accentColor = "#dc2626",
            accentLight = "#fef2f2",
            statusLabel = "DECLINED",
        )
    }

    fun sendLessonReminder(
        email: String,
        lessonSubject: String,
        lessonDate: String,
        tutorName: String,
    ) {
        sendEmail(
            email = email,
            subject = "Reminder: your lesson is coming up",
            title = "Your lesson is coming up!",
            message = """
                Just a friendly reminder that your lesson is approaching.
                Make sure you're ready!
            """.trimIndent(),
            lessonSubject = lessonSubject,
            lessonDate = lessonDate,
            personLabel = "Tutor",
            personName = tutorName,
            accentColor = "#2563eb",
            accentLight = "#eff6ff",
            statusLabel = "UPCOMING",
        )
    }

    fun sendLessonCancelled(
        email: String,
        lessonSubject: String,
        lessonDate: String,
        personName: String,
    ) {
        sendEmail(
            email = email,
            subject = "Your lesson has been cancelled",
            title = "Lesson cancelled",
            message = """
                Please note that this lesson has been cancelled.
                Visit Korkomat to check your other lessons or arrange a new time.
            """.trimIndent(),
            lessonSubject = lessonSubject,
            lessonDate = lessonDate,
            personLabel = "Contact",
            personName = personName,
            accentColor = "#d97706",
            accentLight = "#fffbeb",
            statusLabel = "CANCELLED",
        )
    }

    private fun sendEmail(
        email: String,
        subject: String,
        title: String,
        message: String,
        lessonSubject: String,
        lessonDate: String,
        personLabel: String,
        personName: String,
        accentColor: String,
        accentLight: String,
        statusLabel: String,
    ) {
        val context = Context().apply {
            setVariable("title", title)
            setVariable("message", message)
            setVariable("lessonSubject", lessonSubject)
            setVariable("lessonDate", lessonDate)
            setVariable("personLabel", personLabel)
            setVariable("personName", personName)
            setVariable("accentColor", accentColor)
            setVariable("accentLight", accentLight)
            setVariable("statusLabel", statusLabel)
        }

        val htmlContent = templateEngine.process(
            "email/notification",
            context,
        )

        val mimeMessage = javaMailSender.createMimeMessage()

        val helper = MimeMessageHelper(
            mimeMessage,
            false,
            StandardCharsets.UTF_8.name(),
        )

        helper.setTo(email)
        helper.setFrom(from)
        helper.setSubject(subject)
        helper.setText(htmlContent, true)

        try {
            javaMailSender.send(mimeMessage)
        } catch (e: Exception) {
            logger.error(
                "Failed to send notification email to {}: {}",
                email,
                e.message,
                e,
            )

            throw e
        }
    }
}