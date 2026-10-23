package com.example.korkomat.slot.dto.request

import com.example.korkomat.lesson.entity.enumeration.LessonType
import java.time.Instant
import java.time.LocalDateTime

data class CreateAvailableSlotRequest(
    val type: LessonType = LessonType.OPTIONAL,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
