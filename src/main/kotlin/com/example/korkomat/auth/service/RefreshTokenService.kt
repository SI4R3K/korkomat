package com.example.korkomat.auth.service

import com.example.korkomat.auth.entity.RefreshToken
import com.example.korkomat.user.domain.User

interface RefreshTokenService {
    fun generateRawRefreshToken(): String

    fun saveRefreshToken(rawToken: String, user: User)

    fun findByToken(token: String): RefreshToken

    fun verifyExpiration(token: RefreshToken)
}