package com.example.korkomat.notification.component

import com.example.korkomat.notification.service.NotificationService
import org.springframework.scheduling.annotation.Scheduled

class NotificationScheduler(
    private val notificationService: NotificationService,
) {

    @Scheduled(fixedDelay = 10_000)
    fun processPendingNotifications() {
        notificationService.sendPendingNotifications()
    }
}