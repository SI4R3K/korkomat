package com.example.korkomat.auth.service


import com.example.korkomat.auth.authorization.Role
import com.example.korkomat.auth.config.JwtProperties
import com.example.korkomat.user.domain.User
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtServiceImpl(
    private val jwtProperties: JwtProperties
): JwtService {

    override val expiresIn by lazy {
        jwtProperties.expiration
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

    private fun buildToken(
        extraClaims: Map<String, Role?>?,
        issuer: User,
        expiration: Long
    ): String {
        return Jwts.builder()
            .subject(issuer.email)
            .claim("name", issuer.getFullName())
            .claim("id", issuer.id.toString())
            .claims(extraClaims)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(signingKey)
            .compact()

    }
}