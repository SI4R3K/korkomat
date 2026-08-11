package com.example.korkomat.user.dto.request

import java.math.BigDecimal

data class RegisterTutorRequest(
    val bio: String,
    val hourlyRate: BigDecimal,
)
