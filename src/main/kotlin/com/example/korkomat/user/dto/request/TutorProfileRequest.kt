package com.example.korkomat.user.dto.request

import java.math.BigDecimal

data class EditTutorProfileRequest(
    val newBio: String? = null,
    val newHourlyRate: BigDecimal? = null,
)


