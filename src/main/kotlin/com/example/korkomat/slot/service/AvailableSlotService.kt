package com.example.korkomat.slot.service

import com.example.korkomat.slot.dto.request.AvailableSlotFilterRequest
import com.example.korkomat.slot.dto.request.CreateAvailableSlotRequest
import com.example.korkomat.slot.dto.request.UpdateAvailableSlotRequest
import com.example.korkomat.slot.dto.response.AllAvailableSlotsResponse
import com.example.korkomat.slot.dto.response.AvailableSlotsResponse
import com.example.korkomat.slot.dto.response.CreateAvailableSlotResponse
import com.example.korkomat.slot.dto.response.DeleteAvailableSlotsResponse
import com.example.korkomat.slot.dto.response.UpdateAvailableSlotsResponse

interface AvailableSlotService {
    fun createAvailableSlot(request: CreateAvailableSlotRequest): CreateAvailableSlotResponse
    fun getMyAvailableSlots(): AvailableSlotsResponse
    fun updateAvailableSlot(id: Long, request: UpdateAvailableSlotRequest): UpdateAvailableSlotsResponse
    fun deleteAvailableSlot(id: Long): DeleteAvailableSlotsResponse

    fun searchForAvailableSlots(filter: AvailableSlotFilterRequest): AllAvailableSlotsResponse
}