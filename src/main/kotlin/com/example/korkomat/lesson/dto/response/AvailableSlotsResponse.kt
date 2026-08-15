package com.example.korkomat.lesson.dto.response

import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.lesson.entity.enumeration.LessonType
import com.example.korkomat.lesson.entity.enumeration.SlotStatus
import com.example.korkomat.user.entity.TutorProfile
import java.time.Instant
import java.util.UUID

data class AvailableSlotsResponse(
    val availableSlots: List<AvailableSlotResponse>
)

data class AllAvailableSlotsResponse(
    val allAvailableSlots: List<AvailableSlotResponse>
)

data class AvailableSlotResponse(
    val startTime: Instant,
    val endTime: Instant,
    val status: SlotStatus,
    val type: LessonType,
    val lesson: Lesson? = null,
    val tutorId: UUID? = null
)
