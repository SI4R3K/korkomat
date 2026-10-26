package com.example.korkomat.notification.email.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import org.springframework.web.util.UriComponentsBuilder
import java.nio.charset.StandardCharsets

@Service
class AuthEmailService(
    private val javaMailSender: JavaMailSender,
    private val templateEngine: SpringTemplateEngine,

    @Value("\${spring.mail.username}")
    private val from: String,

    @Value("\${app.frontEndUrl}")
    private val frontendUrl: String,
) {

    private val logger = LoggerFactory.getLogger(
        AuthEmailService::class.java
    )

    fun sendVerificationEmail(email: String, token: String) {
        sendEmail(
            email = email,
            token = token,
            subject = "Verify your email address",
            path = "/auth/verify",
            title = "Welcome to Korkomat!",
            message = """
                Thanks for joining Korkomat!
                Verify your email address to activate your account
                and start using the platform.
            """.trimIndent(),
            buttonText = "Verify email",
            expirationMessage = "For security reasons, this verification link will expire.",
            securityMessage = """
                If you didn't create a Korkomat account, you can safely ignore this email.
            """.trimIndent(),
        )
    }

    fun sendForgotPasswordEmail(email: String, token: String) {
        sendEmail(
            email = email,
            token = token,
            subject = "Reset your Korkomat password",
            path = "/auth/reset-password",
            title = "Reset your password",
            message = """
                We received a request to reset your Korkomat password.
                Click the button below to choose a new password.
            """.trimIndent(),
            buttonText = "Reset password",
            expirationMessage = "For your security, this password-reset link will expire.",
            securityMessage = """
                If you didn't request a password reset, you can safely ignore this email.
                Your password won't change unless you complete the reset process.
            """.trimIndent(),
        )
    }

    private fun sendEmail(
        email: String,
        token: String,
        subject: String,
        path: String,
        title: String,
        message: String,
        buttonText: String,
        expirationMessage: String?,
        securityMessage: String,
    ) {
        try {
            val actionUrl = UriComponentsBuilder
                .fromUriString(frontendUrl)
                .path(path)
                .queryParam("token", token)
                .build()
                .encode()
                .toUriString()

            val context = Context().apply {
                setVariable("title", title)
                setVariable("message", message)
                setVariable("buttonText", buttonText)
                setVariable("actionUrl", actionUrl)
                setVariable("expirationMessage", expirationMessage)
                setVariable("securityMessage", securityMessage)
            }

            val htmlContent = templateEngine.process(
                "email/auth-email",
                context,
            )

            val mimeMessage = javaMailSender.createMimeMessage()

            val helper = MimeMessageHelper(
                mimeMessage,
                false,
                StandardCharsets.UTF_8.name(),
            )

            helper.setTo(email)
            helper.setSubject(subject)
            helper.setFrom(from)
            helper.setText(htmlContent, true)

            javaMailSender.send(mimeMessage)

        } catch (e: Exception) {
            logger.error(
                "Failed to send authentication email to {}: {}",
                email,
                e.message,
                e,
            )

            throw e
        }
    }
}