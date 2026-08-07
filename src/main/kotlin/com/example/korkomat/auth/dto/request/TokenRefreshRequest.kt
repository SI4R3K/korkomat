package com.example.korkomat.auth.dto.request

import jakarta.validation.constraints.NotBlank

data class TokenRefreshRequest(
    @NotBlank
    val refreshToken: String
)

