package com.example.korkomat.user

import com.example.korkomat.auth.dto.response.LoginResponse
import com.example.korkomat.auth.dto.response.TokenRefreshResponse
import com.example.korkomat.auth.entity.ConfirmationToken
import com.example.korkomat.auth.service.ConfirmationTokenService
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.user.entity.User
import com.example.korkomat.user.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.time.Instant

object UserUtil {
    fun tokensToLoginResponse(
        expiresIn: Long?,
        accessToken: String?,
        tokenType: String?,
        refreshToken: String?

    ): LoginResponse {
        return LoginResponse(
            accessToken ?: "No access token provided",
            expiresIn ?: 0,
            tokenType ?: "No type provided",
            refreshToken ?: "No refresh token provided"
        )
    }

    fun tokensToTokenRefreshResponse(
        accessToken: String?,
        expiresIn: Long?,
        tokenType: String?
    ) : TokenRefreshResponse {
        return TokenRefreshResponse(
            accessToken ?: "No access token provided",
            expiresIn ?: 0,
            tokenType ?: "No type provided"
        )
    }
}