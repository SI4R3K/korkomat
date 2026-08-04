package com.example.korkomat.user

import com.example.korkomat.auth.dto.response.LoginResponse

object UserUtil {
    fun tokensToLoginResponse(
        expiresIn: Long?,
        accessToken: String?,

    ): LoginResponse {
        return LoginResponse(
            accessToken ?: "No access token provided",
            expiresIn ?: 0
        )
    }
}