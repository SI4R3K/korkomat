package com.example.korkomat.user.service

interface UserProfileService<REQUEST, RESPONSE> {
    fun register(request: REQUEST): RESPONSE
}