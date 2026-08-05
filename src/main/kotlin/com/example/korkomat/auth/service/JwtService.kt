package com.example.korkomat.auth.service

import com.example.korkomat.auth.authorization.Role
import com.example.korkomat.user.domain.User
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.userdetails.UserDetails
import io.jsonwebtoken.Claims
import java.util.Date
import java.util.function.Function

interface JwtService {
    val expiresIn: Long?
    val tokenType: String?

    fun generateToken(
        extraClaims: Map<String, Role?>,
        issuer: User
    ): String?

    fun getClaimsFromToken(token: String?): Claims?

    fun extractUserDetailFromRequest(
        request: HttpServletRequest
    ): Pair<UserDetails, String>

    fun extractJwtFromHeader(request: HttpServletRequest): String?
    fun extractUsername(token: String): String?
    fun extractExpiration(token: String?): Date?

    fun <T> extractClaim(
        token: String?,
        claimResolver: Function<Claims, T>
    ): T?

    fun isTokenExpired(token: String?): Boolean
    fun isTokenValid(token: String?): Boolean

}


