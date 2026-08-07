package com.example.korkomat.auth.repository

import com.example.korkomat.auth.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {
    fun findByToken(token: String?): RefreshToken?
}