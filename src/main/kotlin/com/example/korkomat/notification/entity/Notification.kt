package com.example.korkomat.notification.entity

import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.notification.entity.enumeration.NotificationStatus
import com.example.korkomat.notification.entity.enumeration.NotificationType
import com.example.korkomat.user.entity.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.Instant

@Entity
@Table(name="notifications")
data class Notification(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    val lesson: Lesson? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: NotificationType,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: NotificationStatus = NotificationStatus.PENDING,

    @Column(nullable = false)
    val scheduledAt: Instant,

    var sentAt: Instant? = null,

    @Column(length = 1000)
    var errorMessage: String? = null,

    val createdAt: Instant = Clock.System.now()
)
