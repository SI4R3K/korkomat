package com.example.korkomat.auth.service

import com.example.korkomat.auth.entity.VerificationToken
import com.example.korkomat.auth.entity.enumeration.VerificationTokenType
import com.example.korkomat.auth.repository.VerificationTokenRepository
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.user.entity.User
import com.example.korkomat.user.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class VerificationTokenServiceImpl(
    private val verificationTokenRepository: VerificationTokenRepository
) : VerificationTokenService {

    companion object {
        private const val EMAIL_CONFIRMATION_EXPIRATION_SECONDS = 60 * 60
        private const val PASSWORD_RESET_EXPIRATION_SECONDS = 15 * 60
    }

    override fun saveVerificationToken(
        verificationToken: VerificationToken
    ): VerificationToken {
        return verificationTokenRepository.save(verificationToken)
    }

    override fun getVerificationByToken(
        verificationToken: String,
        type: VerificationTokenType
    ): VerificationToken {
        return verificationTokenRepository
            .findByTokenAndType(verificationToken, type)
            ?: throw EntityNotFoundException(
                Constant.VERIFICATION_TOKEN_NOT_FOUND
            )
    }

    override fun getVerificationByUser(
        user: User,
        type: VerificationTokenType
    ): VerificationToken {
        return verificationTokenRepository
            .findByUserIdAndType(user.id!!, type)
            ?: throw EntityNotFoundException(
                Constant.VERIFICATION_TOKEN_NOT_FOUND
            )
    }

    @Transactional
    override fun validateVerificationToken(
        verificationToken: String,
        type: VerificationTokenType
    ): VerificationToken {

        val verificationToken = getVerificationByToken(
            verificationToken,
            type
        )

        if (verificationToken.expiresAt.isBefore(Instant.now())) {
            throw IllegalStateException(
                Constant.VERIFICATION_TOKEN_EXPIRED
            )
        }

        if (verificationToken.usedAt != null) {
            throw IllegalStateException(
                Constant.VERIFICATION_TOKEN_ALREADY_USED
            )
        }

        verificationToken.usedAt = Instant.now()

        return verificationToken
    }

    @Transactional
    override fun createOrRenewVerificationToken(
        user: User,
        type: VerificationTokenType
    ): VerificationToken {

        val existingToken = verificationTokenRepository
            .findByUserIdAndType(
                user.id!!,
                type
            )

        if (existingToken != null) {
            existingToken.token = generateToken()
            existingToken.expiresAt = calculateExpiration(type)
            existingToken.usedAt = null

            return existingToken
        }

        val verificationToken = VerificationToken(
            token = generateToken(),
            type = type,
            user = user,
            expiresAt = calculateExpiration(type)
        )

        return verificationTokenRepository
            .save(verificationToken)
    }

    private fun generateToken(): String =
        UUID.randomUUID().toString()

    private fun calculateExpiration(
        type: VerificationTokenType
    ): Instant {

        val expirationSeconds = when (type) {
            VerificationTokenType.EMAIL_CONFIRMATION ->
                EMAIL_CONFIRMATION_EXPIRATION_SECONDS

            VerificationTokenType.PASSWORD_RESET ->
                PASSWORD_RESET_EXPIRATION_SECONDS
        }

        return Instant.now()
            .plusSeconds(expirationSeconds.toLong())
    }

}