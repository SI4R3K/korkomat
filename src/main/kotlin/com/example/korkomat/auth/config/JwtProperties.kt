package com.example.korkomat.auth.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "spring.security.jwt")
data class JwtProperties(
    var secretKey: String = "",
    var expiration: Long = 0,
    var refreshToken: RefreshToken = RefreshToken()
)

data class RefreshToken(
    var expiration: Long = 0,
)
