package com.example.korkomat.auth.service

import com.example.korkomat.auth.dto.request.LoginRequest
import com.example.korkomat.auth.dto.request.LogoutRequest
import com.example.korkomat.auth.dto.request.RegisterRequest
import com.example.korkomat.auth.dto.request.TokenRefreshRequest
import com.example.korkomat.auth.dto.response.LoginResponse
import com.example.korkomat.auth.dto.response.LogoutResponse
import com.example.korkomat.auth.dto.response.RegistrationResponse
import com.example.korkomat.auth.dto.response.TokenRefreshResponse

interface AuthenticationService {
    fun registerUser(request: RegisterRequest): RegistrationResponse
    fun loginUser(request: LoginRequest): LoginResponse
    fun confirmUser(confirmationToken: String)
    fun refreshAccessToken(request: TokenRefreshRequest): TokenRefreshResponse
    fun logout(request: LogoutRequest): LogoutResponse
    fun forgotPassword(email: String)
    fun resetPassword(token: String, password: String)
}
