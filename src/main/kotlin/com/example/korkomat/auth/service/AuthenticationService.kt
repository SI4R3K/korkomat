package com.example.korkomat.auth.service

import com.example.korkomat.auth.dto.request.RegisterRequest

interface AuthenticationService {
    fun registerUser(request: RegisterRequest): String
}