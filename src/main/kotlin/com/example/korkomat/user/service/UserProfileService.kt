package com.example.korkomat.user.service

import com.example.korkomat.user.entity.User

interface UserProfileService<REQUEST, RESPONSE> {
    fun register(request: REQUEST): RESPONSE
}