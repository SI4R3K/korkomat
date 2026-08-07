package com.example.korkomat.auth.dto.response

data class TokenRefreshResponse(
    val accessToken: String?,
    val expiresIn: Long?,
    val tokenType: String = "Bearer"
)
