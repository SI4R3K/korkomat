package com.example.korkomat.slot.dto.request

import com.example.korkomat.lesson.entity.enumeration.LessonType
import java.time.Instant

data class CreateAvailableSlotRequest(
    val type: LessonType = LessonType.OPTIONAL,
    val startTime: Instant,
    val endTime: Instant
)
