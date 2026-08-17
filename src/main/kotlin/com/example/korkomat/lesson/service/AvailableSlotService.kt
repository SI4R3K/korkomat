package com.example.korkomat.lesson.service

import com.example.korkomat.lesson.dto.request.AvailableSlotFilterRequest
import com.example.korkomat.lesson.dto.request.CreateAvailableSlotRequest
import com.example.korkomat.lesson.dto.request.UpdateAvailableSlotRequest
import com.example.korkomat.lesson.dto.response.AllAvailableSlotsResponse
import com.example.korkomat.lesson.dto.response.AvailableSlotsResponse
import com.example.korkomat.lesson.dto.response.CreateAvailableSlotResponse
import com.example.korkomat.lesson.dto.response.DeleteAvailableSlotsResponse
import com.example.korkomat.lesson.dto.response.UpdateAvailableSlotsResponse

interface AvailableSlotService {
    fun createAvailableSlot(request: CreateAvailableSlotRequest): CreateAvailableSlotResponse
    fun getMyAvailableSlots(): AvailableSlotsResponse
    fun updateAvailableSlot(id: Long, request: UpdateAvailableSlotRequest): UpdateAvailableSlotsResponse
    fun deleteAvailableSlot(id: Long): DeleteAvailableSlotsResponse

    fun searchForAvailableSlots(filter: AvailableSlotFilterRequest): AllAvailableSlotsResponse
}