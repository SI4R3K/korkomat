package com.example.korkomat.notification.component

import com.example.korkomat.notification.service.NotificationProcessor
import com.example.korkomat.notification.service.NotificationService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NotificationScheduler(
    private val notificationProcessor: NotificationProcessor,
) {

    @Scheduled(fixedDelay = 5_000)
    fun processPendingNotifications() {
        notificationProcessor.sendPendingNotifications()
    }
}