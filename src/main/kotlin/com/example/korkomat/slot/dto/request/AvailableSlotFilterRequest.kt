package com.example.korkomat.slot.dto.request

import com.example.korkomat.lesson.entity.enumeration.LessonType
import java.time.Instant
import java.util.UUID

data class AvailableSlotFilterRequest(
    val fromTime: Instant? = null,
    val toTime: Instant? = null,
    val tutorId: UUID? = null,
    val lessonType: LessonType? = null,
)
