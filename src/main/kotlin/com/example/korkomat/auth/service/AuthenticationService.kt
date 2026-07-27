package com.example.korkomat.auth.service

import com.example.korkomat.auth.dto.request.LoginRequest
import com.example.korkomat.auth.dto.request.RegisterRequest
import com.example.korkomat.auth.dto.response.LoginResponse

interface AuthenticationService {
    fun registerUser(request: RegisterRequest): String
    fun loginUser(request: LoginRequest): LoginResponse
}