package com.example.korkomat.slot.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.slot.dto.request.AvailableSlotFilterRequest
import com.example.korkomat.slot.dto.response.AllAvailableSlotsResponse
import com.example.korkomat.slot.service.AvailableSlotService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("student/available-slot")
class StudentAvailableSlotController (
    private val availableSlotService: AvailableSlotService,
) {
    @GetMapping("search")
    fun searchForTutorsAvailableSlots(
        @ModelAttribute filter: AvailableSlotFilterRequest
        ): ResponseEntity<Api<AllAvailableSlotsResponse>> {
        val response = availableSlotService
            .searchForAvailableSlots(filter)
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Available slots retrieved successfully."))
    }
}