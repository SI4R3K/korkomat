package com.example.korkomat.user

import com.example.korkomat.auth.dto.response.LoginResponse
import com.example.korkomat.auth.dto.response.TokenRefreshResponse

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