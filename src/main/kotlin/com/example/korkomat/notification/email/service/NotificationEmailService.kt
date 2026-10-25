package com.example.korkomat.notification.email.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class NotificationEmailService(
    private val javaMailSender: JavaMailSender,

    @Value("spring.mail.username")
    private val from: String,

) {

    private fun sendEmail(
        email: String,
        subject: String,
        message: String,
    ) {
        try {
            val content = """
                This is a test notification.
            """.trimIndent()

            val mimeMessage = javaMailSender.createMimeMessage()
            val helper = MimeMessageHelper(mimeMessage, true)

            helper.setTo(email)
            helper.setTo(subject)
            helper.setFrom(from)
            helper.setText(content, true)
            javaMailSender.send(mimeMessage)

        } catch(e: Exception) {
            System.err.println("Failed to send notification: " + e.message)
        }
    }
}