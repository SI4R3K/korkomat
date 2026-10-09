package com.example.korkomat.user.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.user.dto.request.EditTutorProfileRequest
import com.example.korkomat.user.dto.response.TutorProfileResponse
import com.example.korkomat.user.service.TutorProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tutor")
class TutorController(
    val tutorService: TutorProfileService
) {

    @PostMapping("/profile/edit")
    fun editMyTutorProfile(
        @RequestBody request: EditTutorProfileRequest
    ): ResponseEntity<Api<String>> {
        tutorService.editProfile(request)
        val successResponse = Api.ok("Profile updated successfully.")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @GetMapping("/profile")
    fun getMyTutorProfile(): ResponseEntity<Api<TutorProfileResponse>> {
        val myProfileInfo = tutorService.getMyTutorProfile()
        val successResponse = Api.ok(myProfileInfo, "Profile info retrieved successfully.")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }
}