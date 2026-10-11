package com.example.korkomat.user.dto.request

data class EditMyProfileRequest(
    val newFirstName: String? = null,
    val newLastName: String? = null,
    val newPhoneNumber: String? = null,
    val newLocation: String? = null,
)
