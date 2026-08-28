package com.example.korkomat.auth.dto.request

data class ResetPasswordRequest(
    val token: String,
    val password: String
)
