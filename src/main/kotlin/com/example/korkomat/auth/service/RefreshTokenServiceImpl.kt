package com.example.korkomat.auth.service

import com.example.korkomat.auth.config.JwtProperties
import com.example.korkomat.auth.entity.RefreshToken
import com.example.korkomat.auth.exceptions.ExpiredJwtException
import com.example.korkomat.auth.exceptions.RefreshTokenExpiredException
import com.example.korkomat.auth.exceptions.RefreshTokenNotFoundExcpetion
import com.example.korkomat.auth.repository.RefreshTokenRepository
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.toJavaInstant
import kotlin.time.toKotlinDuration

@Service
class RefreshTokenServiceImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProperties: JwtProperties
): RefreshTokenService {

    override fun generateRawRefreshToken(): String {
        return UUID.randomUUID().toString()
    }

    override fun findByToken(token: String): RefreshToken {

//        val hashToken = RefreshToken.encryptToken(token) no refresh token hashing for now

        return refreshTokenRepository.findByToken(token)
            ?: throw RefreshTokenNotFoundExcpetion(Constant.REFRESH_TOKEN_NOT_FOUND)
    }

    override fun verifyExpiration(token: RefreshToken) {
        if (token.expiresAt < (Clock.System.now()).toJavaInstant()) {
            refreshTokenRepository.delete(token)
            throw RefreshTokenExpiredException("Refresh token was expired. Please make a new signin request")
        }
    }

    @Transactional
    override fun saveRefreshToken(
        rawToken: String,
        user: User
    ) {
//        val tokenHash = RefreshToken.encryptToken(rawToken) for now not implementing hashing refresh token

        refreshTokenRepository.save(
            RefreshToken(
                token = rawToken,
                expiresAt = (
                        Clock.System.now()
                            .plus(jwtProperties.refreshToken.expiration.toKotlinDuration()
                            )
                        ).toJavaInstant(),
                isRevoked = false,
                user = user
            )
        )
    }
}