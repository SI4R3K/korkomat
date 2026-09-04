package com.example.korkomat.user.controller

import com.example.korkomat.auth.dto.response.LoginResponse
import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.user.dto.request.RegisterStudentRequest
import com.example.korkomat.user.dto.request.RegisterTutorRequest
import com.example.korkomat.user.dto.response.RegisterStudentResponse
import com.example.korkomat.user.dto.response.RegisterTutorResponse
import com.example.korkomat.user.dto.response.UserInfoResponse
import com.example.korkomat.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController(
    val userService: UserService,
) {

    @PostMapping("register/student")
    fun registerStudent(@RequestBody request: RegisterStudentRequest): ResponseEntity<Api<RegisterStudentResponse>> {
        val registerStudentResponse = userService.registerStudent(request)
        val successResponse = Api.ok(registerStudentResponse, "Student profile registered successfully!")
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse)
    }

    @PostMapping("register/tutor")
    fun registerTutor(@RequestBody request: RegisterTutorRequest): ResponseEntity<Api<RegisterTutorResponse>> {
        val registerTutorResponse = userService.registerTutor(request)
        val successResponse = Api.ok(registerTutorResponse, "Tutor progile registered successfully!")
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse)
    }

    @GetMapping("get/me")
    fun getUserInfo(): ResponseEntity<Api<UserInfoResponse>> {
        val userInfoResponse = userService.getCurrentUserInfo()
        val successResponse = Api.ok(userInfoResponse, "User info retrieved successfully!")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }
}