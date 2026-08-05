package com.example.korkomat.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "spring.security.jwt")
data class JwtProperties(
    var secretKey: String = "",
    var expiration: Long = 0,
    var refreshToken: RefreshToken = RefreshToken()
)

data class RefreshToken(
    var expiration: Duration = Duration.ZERO,
)
