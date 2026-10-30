package com.example.korkomat.notification.service

import com.example.korkomat.lesson.entity.Lesson

interface NotificationService {

    fun notifyStudentLessonConfirmed(lesson: Lesson)
    fun notifyTutorLessonBooked(lesson: Lesson)
    fun notifyStudentLessonRejected(lesson: Lesson)

    fun sendPendingNotifications()
}