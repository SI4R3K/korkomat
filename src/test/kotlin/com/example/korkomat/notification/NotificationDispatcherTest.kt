package com.example.korkomat.notification

import com.example.korkomat.notification.component.NotificationHandler
import com.example.korkomat.notification.entity.Notification
import com.example.korkomat.notification.entity.enumeration.NotificationType
import com.example.korkomat.notification.service.NotificationDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class NotificationDispatcherTest {

    @Mock
    private lateinit var lessonConfirmedHandler: NotificationHandler

    private lateinit var dispatcher: NotificationDispatcher

    @BeforeEach
    fun setUp() {
        whenever(lessonConfirmedHandler.type)
            .thenReturn(NotificationType.LESSON_CONFIRMED)

        dispatcher = NotificationDispatcher(
            handlers = listOf(lessonConfirmedHandler)
        )
    }

    @Test
    fun `should dispatch notification to correct handler`() {
        val notification = mock<Notification> {
            on { type } doReturn NotificationType.LESSON_CONFIRMED
        }

        dispatcher.send(notification)

        verify(lessonConfirmedHandler).send(notification)
    }
}