package com.example.korkomat.auth.controller

import com.example.korkomat.auth.dto.request.LoginRequest
import com.example.korkomat.auth.dto.request.RegisterRequest
import com.example.korkomat.auth.dto.response.LoginResponse
import com.example.korkomat.auth.service.AuthenticationService
import com.example.korkomat.common.dto.response.Api
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("auth")
class AuthController(
    val authService: AuthenticationService
) {
    @PostMapping("login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Api<LoginResponse>> {
        val userResponse = authService.loginUser(request)
        val successResponse = Api.ok(userResponse, "Login successful")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @PostMapping("register")
    fun register(@RequestBody request: RegisterRequest): String {
        return authService.registerUser(request)
    }
}