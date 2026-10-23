package com.example.korkomat.slot.dto.request

import com.example.korkomat.lesson.entity.enumeration.LessonType
import java.time.Instant
import java.time.LocalDateTime

data class UpdateAvailableSlotRequest(
    val startTime: LocalDateTime? = null,
    val endTime: LocalDateTime? = null,
    val type: LessonType? = null,
    )
