package com.example.korkomat.auth.dto.response

data class LoginResponse(
    val accessToken: String,
    val expiresIn: Long,
    val tokenType: String?,
    val refreshToken: String?,
)
