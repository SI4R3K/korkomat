package com.example.korkomat.notification.service

import com.example.korkomat.lesson.entity.Lesson

interface NotificationService {

    fun notifyLessonConfirmed(lesson: Lesson)

    fun sendPendingNotifications()
}