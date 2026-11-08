package com.example.korkomat.notification

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.Instant

object NotificationDateUtil {
    private val formatter = DateTimeFormatter
        .ofPattern("dd.MM.yyyy HH:mm")
        .withZone(ZoneId.of("Europe/Warsaw"))

    fun formatDate(
        startTime: Instant,
        endTime: Instant
        ): String {
        return "from ${formatter.format(startTime)} " +
                "to ${formatter.format(endTime)}"
    }
}