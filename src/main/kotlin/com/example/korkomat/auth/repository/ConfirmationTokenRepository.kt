package com.example.korkomat.auth.repository

import com.example.korkomat.auth.entity.ConfirmationToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ConfirmationTokenRepository: JpaRepository<ConfirmationToken, Long> {
    fun findByToken(token: String): ConfirmationToken?

    fun findByUserId(userId: UUID): ConfirmationToken?
}