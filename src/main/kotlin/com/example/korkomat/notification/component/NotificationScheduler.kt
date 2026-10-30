package com.example.korkomat.notification.component

import com.example.korkomat.notification.service.NotificationService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NotificationScheduler(
    private val notificationService: NotificationService,
) {

    @Scheduled(fixedDelay = 5_000)
    fun processPendingNotifications() {
        notificationService.sendPendingNotifications()
    }
}