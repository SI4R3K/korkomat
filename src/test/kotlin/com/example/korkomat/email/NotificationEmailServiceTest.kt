package com.example.korkomat.email

import com.example.korkomat.notification.email.service.NotificationEmailService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NotificationEmailServiceTest(
    @Autowired
    private val emailService: NotificationEmailService
) {
    @Test
    fun `should send lesson confirmed email`() {
        emailService.sendLessonConfirmed(
            email = "antosiok@gmail.com",
            lessonSubject = "Matematyka",
            lessonDate = "from 15.11.2026 10:00 to 15.11.2026 12:00",
            tutorName = "Jan Kowalski"
        )
    }

    @Test
    fun `should send lesson rejected email`() {
        emailService.sendLessonRejected(
            email = "antosiok@gmail.com",
            lessonSubject = "Matematyka",
            lessonDate = "from 15.11.2026 10:00 to 15.11.2026 12:00",
            tutorName = "Jan Kowalski"
        )
    }

    @Test
    fun `should send lesson booked email`() {
        emailService.sendLessonBooked(
            email = "antosiok@gmail.com",
            lessonSubject = "Matematyka",
            lessonDate = "from 15.11.2026 10:00 to 15.11.2026 12:00",
            studentName = "Jan Kowalski",
        )
    }
}