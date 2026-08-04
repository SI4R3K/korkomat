package com.example.korkomat.auth.service

import com.example.korkomat.auth.authorization.Role
import com.example.korkomat.user.domain.User

interface JwtService {
    fun generateToken(
        extraClaims: Map<String, Role?>,
        issuer: User
    ): String?

    val expiresIn: Long?
}


