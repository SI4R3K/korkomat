package com.example.korkomat.lesson.service

import com.example.korkomat.lesson.dto.request.CreateAvailableSlotRequest
import com.example.korkomat.lesson.dto.response.CreateAvailableSlotResponse

interface AvailableSlotService {
    fun createAvailableSlot(request: CreateAvailableSlotRequest): CreateAvailableSlotResponse

}