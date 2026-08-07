package com.example.korkomat.auth.service

import com.example.korkomat.auth.repository.RefreshTokenRepository
import com.example.korkomat.auth.authorization.Role
import com.example.korkomat.auth.config.JwtProperties
import com.example.korkomat.auth.entity.RefreshToken
import com.example.korkomat.auth.exceptions.ExpiredJwtException
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.user.domain.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
import java.util.Optional
import java.util.UUID
import java.util.function.Function
import kotlin.time.Clock
import kotlin.time.toKotlinDuration

@Service
class JwtServiceImpl(
    private val jwtProperties: JwtProperties,
    private val customUserDetailService: CustomUserDetailService,
    private val refreshTokenRepository: RefreshTokenRepository,
): JwtService {

    override val expiresIn by lazy {
        jwtProperties.expiration
    }

    override val tokenType by lazy {
        "Bearer"
    }

    private val signingKey by lazy {
        Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(jwtProperties.secretKey)
        )
    }

    override fun generateToken(
        extraClaims: Map<String, Role?>,
        issuer: User
    ): String? {
        return buildToken(extraClaims, issuer, jwtProperties.expiration)
    }

    override fun getClaimsFromToken(token: String?): Claims? {
        return try {
            Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: Exception) {
            null
        }
    }

    override fun <T> extractClaim(
        token: String?,
        claimResolver: Function<Claims, T>
    ): T? {
        val claims = getClaimsFromToken(token)
            ?: throw MalformedJwtException(Constant.JWT_MALFORMED)

        return claimResolver.apply(claims)
    }

    override fun extractJwtFromHeader(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION)
        return if (
            bearerToken != null && bearerToken.startsWith("Bearer ")
        ) {
            bearerToken.substring(7)
        } else {
            null
        }
    }

    override fun extractUsername(token: String): String? {
        return extractClaim(token) { it.subject }
    }

    override fun extractExpiration(token: String?): Date? {
        return extractClaim(token) {it.expiration}
    }

    override fun isTokenValid(token: String?): Boolean {
        return try {
            getClaimsFromToken(token)
                ?: throw MalformedJwtException(Constant.JWT_MALFORMED)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token)?.before(Date()) == true
    }

    private fun buildToken(
        extraClaims: Map<String, Role?>?,
        issuer: User,
        expiration: Long
    ): String {
        return Jwts.builder()
            .header().add(mapOf("type" to "Bearer")).and()
            .subject(issuer.email)
            .claim("name", issuer.getFullName())
            .claim("id", issuer.id.toString())
            .claims(extraClaims)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(signingKey)
            .compact()

    }

    @Transactional(readOnly = true)
    override fun extractUserDetailFromRequest(request: HttpServletRequest): Pair<UserDetails, String> {
        val token = extractJwtFromHeader(request)
            ?: throw MalformedJwtException(Constant.JWT_MALFORMED)

        if (isTokenExpired(token)) {
            throw ExpiredJwtException(Constant.JWT_EXPIRED)
        }

        if (!isTokenValid(token)) {
            throw MalformedJwtException(Constant.JWT_MALFORMED)
        }

        val username = extractUsername(token)
            ?: throw MalformedJwtException(Constant.JWT_MALFORMED)

        val userDetails = customUserDetailService.loadUserByUsername(username)

        return Pair(userDetails, token)
    }


}