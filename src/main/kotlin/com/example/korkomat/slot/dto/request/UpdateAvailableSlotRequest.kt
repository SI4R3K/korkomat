package com.example.korkomat.slot.dto.request

import com.example.korkomat.lesson.entity.enumeration.LessonType
import java.time.Instant

data class UpdateAvailableSlotRequest(
    val startTime: Instant? = null,
    val endTime: Instant? = null,
    val type: LessonType? = null,
    )
