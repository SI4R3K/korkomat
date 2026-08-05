package com.example.korkomat.user

import com.example.korkomat.auth.dto.response.LoginResponse

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
}