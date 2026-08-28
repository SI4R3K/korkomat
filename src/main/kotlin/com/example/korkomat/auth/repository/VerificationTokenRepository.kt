package com.example.korkomat.auth.repository

import com.example.korkomat.auth.entity.VerificationToken
import com.example.korkomat.auth.entity.enumeration.VerificationTokenType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface VerificationTokenRepository: JpaRepository<VerificationToken, Long> {
    fun findByTokenAndType(
        token: String,
        type: VerificationTokenType
    ): VerificationToken?

    fun findByUserIdAndType(
        userId: UUID,
        type: VerificationTokenType
    ): VerificationToken?
}