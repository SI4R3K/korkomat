package com.example.korkomat.user.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.user.dto.response.TutorProfileResponse
import com.example.korkomat.user.service.StudentProfileServiceImpl
import com.example.korkomat.user.service.TutorProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/student")
class StudentController(
    val tutorService: TutorProfileService
) {
    @GetMapping("/get/tutor/{tutorId}")
    fun getTutorPublicDetails(
        @PathVariable("tutorId") tutorId: UUID
    ): ResponseEntity<Api<TutorProfileResponse>> {
        val response = tutorService.getTutorProfile(tutorId)
        val successResponse = Api.ok(response, "Tutor profile info retrieved successfully")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }
}