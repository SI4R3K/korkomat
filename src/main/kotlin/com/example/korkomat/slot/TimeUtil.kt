package com.example.korkomat.slot

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object TimeUtil {

    private val ZONE_ID = ZoneId.of("Europe/Warsaw")

    fun localDateTimeToInstant(localDateTime: LocalDateTime): Instant {
         return localDateTime.atZone(ZONE_ID).toInstant()
    }
}