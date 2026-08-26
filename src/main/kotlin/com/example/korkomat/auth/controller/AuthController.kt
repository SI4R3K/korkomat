package com.example.korkomat.auth.controller

import com.example.korkomat.auth.dto.request.LoginRequest
import com.example.korkomat.auth.dto.request.LogoutRequest
import com.example.korkomat.auth.dto.request.RegisterRequest
import com.example.korkomat.auth.dto.request.TokenRefreshRequest
import com.example.korkomat.auth.dto.response.LoginResponse
import com.example.korkomat.auth.dto.response.LogoutResponse
import com.example.korkomat.auth.dto.response.RegistrationResponse
import com.example.korkomat.auth.dto.response.TokenRefreshResponse
import com.example.korkomat.auth.entity.ConfirmationToken
import com.example.korkomat.auth.service.AuthenticationService
import com.example.korkomat.auth.service.JwtService
import com.example.korkomat.common.dto.response.Api
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<Api<RegistrationResponse>> {
        val response = authService.registerUser(request)
        val successResponse = Api.ok(response, "register successful")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @PostMapping("refresh-token")
    fun refreshToken(@RequestBody request: TokenRefreshRequest): ResponseEntity<Api<TokenRefreshResponse>> {
        val tokenRefreshResponse = authService.refreshAccessToken(request)
        val successResponse = Api.ok(tokenRefreshResponse, "Refresh successful")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @PostMapping("logout")
    fun logout(@RequestBody request: LogoutRequest): ResponseEntity<Api<LogoutResponse>> {
        val logoutResponse = authService.logout(request)
        val successResponse = Api.ok(logoutResponse, "Logout successful")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @GetMapping("verify")
    fun verifyEmail(@RequestParam token: String): ResponseEntity<Api<Unit>> {
        val verifyResponse = authService.confirmUser(token)
        val successResponse = Api.ok(verifyResponse, "Confirm successful")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }
}