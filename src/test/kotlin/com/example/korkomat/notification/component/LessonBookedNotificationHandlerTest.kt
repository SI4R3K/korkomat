package com.example.korkomat.notification.component

import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.notification.email.service.NotificationEmailService
import com.example.korkomat.notification.entity.Notification
import com.example.korkomat.notification.entity.enumeration.NotificationType
import com.example.korkomat.slot.entity.AvailableSlot
import com.example.korkomat.subject.entity.Subject
import com.example.korkomat.subject.entity.TutorSubject
import com.example.korkomat.user.entity.StudentProfile
import com.example.korkomat.user.entity.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.Instant

@ExtendWith(MockitoExtension::class)
class LessonBookedNotificationHandlerTest {

    @Mock
    private lateinit var emailService: NotificationEmailService

    private lateinit var handler: LessonBookedNotificationHandler

    @BeforeEach
    fun setUp() {
        handler = LessonBookedNotificationHandler(emailService)
    }

    @Test
    fun `should send lesson booked notification`() {
        val tutor = mock<User> {
            on { email } doReturn "tutor@gmail.com"
        }

        val student = mock<User> {
            on { getFullName() } doReturn "Jan Kowalski"
        }

        val studentProfile = mock<StudentProfile> {
            on { user } doReturn student
        }

        val subject = mock<Subject> {
            on { name } doReturn "Matematyka"
        }

        val tutorSubject = mock<TutorSubject> {
            on { this.subject } doReturn subject
        }

        val slot = mock<AvailableSlot> {
            on { startTime } doReturn Instant.parse("2026-11-15T09:00:00Z")
            on { endTime } doReturn Instant.parse("2026-11-15T10:00:00Z")
        }

        val lesson = mock<Lesson> {
            on { this.studentProfile } doReturn studentProfile
            on { this.tutorSubject } doReturn tutorSubject
            on { this.slot } doReturn slot
        }

        val notification = mock<Notification> {
            on { user } doReturn tutor
            on { this.lesson } doReturn lesson
        }

        handler.send(notification)

        verify(emailService).sendLessonBooked(
            email = eq("tutor@gmail.com"),
            lessonSubject = eq("Matematyka"),
            lessonDate = any(),
            studentName = eq("Jan Kowalski")
        )
    }
}