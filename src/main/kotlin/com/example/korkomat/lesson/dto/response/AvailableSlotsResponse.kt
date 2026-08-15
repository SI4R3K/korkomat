package com.example.korkomat.lesson.dto.response

import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.lesson.entity.enumeration.LessonType
import com.example.korkomat.lesson.entity.enumeration.SlotStatus
import java.time.Instant

data class AvailableSlotsResponse(
    val availableSlots: List<AvailableSlotResponse>
)

data class AvailableSlotResponse(
    val startTime: Instant,
    val endTime: Instant,
    val status: SlotStatus,
    val type: LessonType,
    val lesson: Lesson? = null
)
