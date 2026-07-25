package com.example.korkomat.auth.controller

import com.example.korkomat.auth.dto.request.RegisterRequest
import com.example.korkomat.auth.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("auth")
class AuthController(
    val authService: AuthenticationService
) {

    @PostMapping("register")
    fun register(@RequestBody request: RegisterRequest): String {
        return authService.registerUser(request)
    }
}