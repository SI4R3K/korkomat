package com.example.korkomat.slot.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.slot.dto.request.CreateAvailableSlotRequest
import com.example.korkomat.slot.dto.request.UpdateAvailableSlotRequest
import com.example.korkomat.slot.dto.response.AvailableSlotsResponse
import com.example.korkomat.slot.dto.response.CreateAvailableSlotResponse
import com.example.korkomat.slot.dto.response.DeleteAvailableSlotsResponse
import com.example.korkomat.slot.dto.response.UpdateAvailableSlotsResponse
import com.example.korkomat.slot.service.AvailableSlotService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("tutor/available-slot")
class TutorAvailableSlotController(
    private val availableSlotService: AvailableSlotService
) {

    @PostMapping
    fun createAvailableSlot(@RequestBody request: CreateAvailableSlotRequest): ResponseEntity<Api<CreateAvailableSlotResponse>> {
        val response = availableSlotService.createAvailableSlot(request)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Api.ok(response, "Available slot was created."))
    }

    @GetMapping
    fun getMyAvailableSlots(): ResponseEntity<Api<AvailableSlotsResponse>> {
        val response = availableSlotService.getMyAvailableSlots()
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Fetched all slots for the tutor."))
    }

    @PutMapping("/{id}")
    fun updateAvailableSlot(
        @PathVariable id: Long,
        @RequestBody request: UpdateAvailableSlotRequest
    ): ResponseEntity<Api<UpdateAvailableSlotsResponse>> {
        val response = availableSlotService.updateAvailableSlot(id, request)
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Updating slot was successful."))
    }

    @DeleteMapping("/{id}")
    fun deleteAvailableSlot(
        @PathVariable id: Long
    ): ResponseEntity<Api<DeleteAvailableSlotsResponse>> {
        val response = availableSlotService.deleteAvailableSlot(id)
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Deleting slot was successful."))
    }
}