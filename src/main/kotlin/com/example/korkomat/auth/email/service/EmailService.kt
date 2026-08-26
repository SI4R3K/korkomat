package com.example.korkomat.auth.email.service

import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@Service
class EmailService(
    private val javaMailSender: JavaMailSender,

    @Value("spring.mail.username")
    private val from: String,
) {
    fun sendVerificationEmail(email: String, token: String) {
        val subject: String = "Email verification"
        val path: String = "/auth/verify"
        val message = "Click the button below to verify your email."
        sendEmail(email, token, subject, path, message)
    }

    fun sendForgotPasswordEmail(email: String, token: String) {
        val subject: String = "Password Reset Request"
        val path: String = "/reset-password"
        val message = "Click the button below to reset your password."
        sendEmail(email, token, subject, path, message)
    }

    private fun sendEmail(
        email: String,
        token: String,
        subject: String,
        path: String,
        message: String
    ) {
        try {
            val actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path)
                .queryParam("token", token)
                .toUriString()

            val content = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border-radius: 8px; background-color: #f9f9f9; text-align: center;">
                        <h2 style="color: #333;">$subject</h2>
                        <p style="font-size: 16px; color: #555;">$message</p>
                        <a href="$actionUrl" style="display: inline-block; margin: 20px 0; padding: 10px 20px; font-size: 16px; color: #fff; background-color: #007bff; text-decoration: none; border-radius: 5px;">
                            Proceed
                        </a>
                        <p style="font-size: 14px; color: #777;">
                            Or copy and paste this link into your browser:
                        </p>
                        <p style="font-size: 14px; color: #007bff;">$actionUrl</p>
                        <p style="font-size: 12px; color: #aaa;">
                            This is an automated message. Please do not reply.
                        </p>
                    </div>
            """.trimIndent()

            val mimeMessage = javaMailSender.createMimeMessage()
            val helper = MimeMessageHelper(mimeMessage, true)

            helper.setTo(email)
            helper.setSubject(subject)
            helper.setFrom(from)
            helper.setText(content, true)
            javaMailSender.send(mimeMessage)

        } catch (e: Exception) {
            System.err.println("Failed to send email: "+e.message)
        }
    }
}