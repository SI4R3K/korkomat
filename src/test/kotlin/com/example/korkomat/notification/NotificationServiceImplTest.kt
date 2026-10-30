package com.example.korkomat.notification

import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.notification.email.service.NotificationEmailService
import com.example.korkomat.notification.entity.Notification
import com.example.korkomat.notification.entity.enumeration.NotificationStatus
import com.example.korkomat.notification.entity.enumeration.NotificationType
import com.example.korkomat.notification.repository.NotificationRepository
import com.example.korkomat.notification.service.NotificationService
import com.example.korkomat.user.entity.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant

@ExtendWith(MockitoExtension::class)
class NotificationServiceImplTest {

    @Mock
    private lateinit var notificationRepository: NotificationRepository

    @Mock
    private lateinit var notificationService: NotificationService

    @Mock
    private lateinit var emailService: NotificationEmailService

    @Test
    fun `should process pending lesson confirmation notification`() {
        // given
        val user = mock<User>()
        whenever(user.email).thenReturn("student@example.com")

        // Deep stubs pozwalają przygotować zagnieżdżone relacje
        // bez tworzenia wszystkich encji domenowych.
        val lesson = mock<Lesson>(Answers.RETURNS_DEEP_STUBS)

        whenever(lesson.slot.startTime)
            .thenReturn(Instant.parse("2026-11-15T09:00:00Z"))

        whenever(lesson.slot.endTime)
            .thenReturn(Instant.parse("2026-11-15T10:00:00Z"))

        whenever(lesson.slot.tutorProfile.user.getFullName())
            .thenReturn("Jan Kowalski")

        val notification = Notification(
            user = user,
            lesson = lesson,
            type = NotificationType.LESSON_CONFIRMED,
            status = NotificationStatus.PENDING
        )

        whenever(
            notificationRepository.findAllByStatus(
                NotificationStatus.PENDING
            )
        ).thenReturn(listOf(notification))

        // when
        notificationService.sendPendingNotifications()

        // then
        verify(emailService).sendLessonConfirmed(
            email = eq("student@example.com"),
            lessonSubject = any(),
            lessonDate = any(),
            tutorName = eq("Jan Kowalski")
        )

        assertEquals(NotificationStatus.SENT, notification.status)

        verify(notificationRepository).save(notification)
    }
}