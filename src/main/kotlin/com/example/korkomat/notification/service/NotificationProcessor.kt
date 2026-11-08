package com.example.korkomat.notification.service

import com.example.korkomat.notification.entity.enumeration.NotificationStatus
import com.example.korkomat.notification.repository.NotificationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationProcessor(
    private val notificationRepository: NotificationRepository,
    private val notificationDispatcher: NotificationDispatcher,
) {

    private val logger = LoggerFactory.getLogger(NotificationProcessor::class.java)

    @Transactional
    fun sendPendingNotifications() {
        val notifications = notificationRepository
            .findAllByStatus(NotificationStatus.PENDING)

        logger.info(
            "Found {} pending notifications",
            notifications.size
        )

        notifications.forEach { notification ->
            try {
                logger.info(
                    "Processing notification id={}, type={}, status={}",
                    notification.id,
                    notification.type,
                    notification.status
                )

                notificationDispatcher.send(notification)
                notification.markAsSent()
            } catch (e: Exception) {
                notification.markAsFailed(e.message)
            }

            notificationRepository.save(notification)
        }
    }
}