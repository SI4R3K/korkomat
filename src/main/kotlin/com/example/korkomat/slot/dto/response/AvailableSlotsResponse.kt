package com.example.korkomat.slot.dto.response

import com.example.korkomat.lesson.entity.enumeration.LessonType
import com.example.korkomat.slot.entity.enumeration.SlotStatus
import java.time.Instant

data class AvailableSlotsResponse(
    val availableSlots: List<AvailableSlotResponse>
)

data class AllAvailableSlotsResponse(
    val allAvailableSlots: List<SearchAvailableSlotsResponse>
)

data class UpdateAvailableSlotsResponse(
    val message: String,
    val updatedAvailableSlot: AvailableSlotResponse
)

data class DeleteAvailableSlotsResponse(
    val message: String,
    val deletedAvailableSlot: AvailableSlotResponse
)

data class AvailableSlotResponse(
    val slotId: Long?,
    val startTime: Instant,
    val endTime: Instant,
    val status: SlotStatus,
    val type: LessonType,
    val lessonId: Long? = null,
)

data class SearchAvailableSlotsResponse(
    val slotId: Long?,
    val tutorName: String?,
    val startTime: Instant,
    val endTime: Instant,
    val type: LessonType,
)

