package com.example.korkomat.notification.service

interface NotificationService {
    fun sendNotification()

    fun sendPendingNotifications()
}