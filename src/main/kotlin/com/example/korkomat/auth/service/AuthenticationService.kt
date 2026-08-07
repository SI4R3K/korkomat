package com.example.korkomat.auth.service

import com.example.korkomat.auth.dto.request.LoginRequest
import com.example.korkomat.auth.dto.request.LogoutRequest
import com.example.korkomat.auth.dto.request.RegisterRequest
import com.example.korkomat.auth.dto.request.TokenRefreshRequest
import com.example.korkomat.auth.dto.response.LoginResponse
import com.example.korkomat.auth.dto.response.LogoutResponse
import com.example.korkomat.auth.dto.response.TokenRefreshResponse

interface AuthenticationService {
    fun registerUser(request: RegisterRequest): String
    fun loginUser(request: LoginRequest): LoginResponse
    fun refreshAccessToken(request: TokenRefreshRequest): TokenRefreshResponse
    fun logout(request: LogoutRequest): LogoutResponse
}
