package com.example.korkomat.lesson.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.lesson.dto.request.CreateAvailableSlotRequest
import com.example.korkomat.lesson.dto.response.AvailableSlotsResponse
import com.example.korkomat.lesson.dto.response.CreateAvailableSlotResponse
import com.example.korkomat.lesson.service.AvailableSlotService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/available-slot")
class AvailableSlotController(
    private val availableSlotService: AvailableSlotService
) {

    @PostMapping("create")
    fun createAvailableSlot(@RequestBody request: CreateAvailableSlotRequest): ResponseEntity<Api<CreateAvailableSlotResponse>> {
        val response = availableSlotService.createAvailableSlot(request)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Api.ok(response, "Available slot was created."))
    }

    @GetMapping
    fun getAvailableSlots(): ResponseEntity<Api<AvailableSlotsResponse>> {
        val response = availableSlotService.getAvailableSlots()
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Fetched all slots for the tutor."))
    }
}