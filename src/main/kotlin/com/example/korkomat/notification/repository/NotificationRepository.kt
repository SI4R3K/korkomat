package com.example.korkomat.notification.repository

import com.example.korkomat.notification.entity.Notification
import com.example.korkomat.notification.entity.enumeration.NotificationStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID
import kotlin.time.Instant

interface NotificationRepository: JpaRepository<Notification, UUID> {

    fun findAllByStatusAndScheduledAtLessThanEqual(
        status: NotificationStatus,
        scheduledAt: Instant
    ): List<Notification>

    fun findAllByStatus(
        status: NotificationStatus
    ): List<Notification>
}