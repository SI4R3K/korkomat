package com.example.korkomat.notification.entity.enumeration

enum class NotificationType {
    LESSON_BOOKED,
    LESSON_CONFIRMED,
    LESSON_REJECTED,
    LESSON_CANCELLED,
    LESSON_REMINDER_24H,
    LESSON_REMINDER_1H
}

enum class NotificationStatus {
    PENDING,
    SENT,
    FAILED,
    CANCELLED
}