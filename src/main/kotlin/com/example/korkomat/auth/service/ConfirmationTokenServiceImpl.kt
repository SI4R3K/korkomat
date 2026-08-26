package com.example.korkomat.auth.service

import com.example.korkomat.auth.entity.ConfirmationToken
import com.example.korkomat.auth.repository.ConfirmationTokenRepository
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.user.entity.User
import com.example.korkomat.user.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class ConfirmationTokenServiceImpl(
    private val confirmationTokenRepository: ConfirmationTokenRepository,
    private val userRepository: UserRepository,
) : ConfirmationTokenService {

    companion object {
        private const val TOKEN_EXPIRATION_SECONDS = (60*60).toLong()
    }
    override fun saveConfirmationToken(confirmationToken: ConfirmationToken): ConfirmationToken {
        return confirmationTokenRepository.save(confirmationToken)
    }

    override fun getConfirmationByToken(token: String): ConfirmationToken {
        return confirmationTokenRepository.findByToken(token)
            ?: throw EntityNotFoundException(Constant.CONFIRMATION_TOKEN_NOT_FOUND)
    }

    override fun getConfirmationByUser(user: User): ConfirmationToken {
        return confirmationTokenRepository.findByUserId(user.id!!)
            ?: throw EntityNotFoundException(Constant.CONFIRMATION_TOKEN_NOT_FOUND)
    }

    @Transactional
    override fun validateConfirmationToken(confirmationToken: String) {

        val token = getConfirmationByToken(confirmationToken)

        if (token.expiresAt.isBefore(Instant.now())) {
            throw IllegalStateException(
                Constant.CONFIRMATION_TOKEN_EXPIRED
            )
        }

        if (token.confirmedAt != null) {
            throw IllegalStateException(
                Constant.CONFIRMATION_TOKEN_ALREADY_CONFIRMED
            )
        }

        val user = token.user

        user.isActive = true
        token.confirmedAt = Instant.now()
    }

    @Transactional
    override fun createOrRenewConfirmationToken(
        user: User
    ): ConfirmationToken {

        val existingToken =
            confirmationTokenRepository.findByUserId(user.id!!)

        if (existingToken != null) {
            existingToken.token = generateToken()
            existingToken.expiresAt = calculateExpiration()
            existingToken.confirmedAt = null

            return existingToken
        }

        val confirmationToken = ConfirmationToken(
            token = generateToken(),
            user = user,
            expiresAt = calculateExpiration()
        )

        return confirmationTokenRepository
            .save(confirmationToken)
    }

    private fun generateToken(): String =
        UUID.randomUUID().toString()

    private fun calculateExpiration(): Instant =
        Instant.now().plusSeconds(TOKEN_EXPIRATION_SECONDS)
}