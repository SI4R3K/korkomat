package com.example.korkomat.notification.component

import com.example.korkomat.notification.entity.Notification
import com.example.korkomat.notification.entity.enumeration.NotificationType

interface NotificationHandler {

    val type: NotificationType

    fun send(notification: Notification)
}