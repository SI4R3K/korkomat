package com.example.korkomat.auth.entity

import com.example.korkomat.user.domain.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.Instant

@Entity(name = "refresh_tokens")
data class RefreshToken(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    var token: String?,

    @Column(name = "expires_at", nullable = false)
    val expiresAt: Instant,

    @Column(name = "is_revoked", nullable = false)
    var isRevoked: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY) //, cascade = [CascadeType.REMOVE]) is it helpfull or not?
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    ) {
    companion object {
        fun encryptToken(token: String): String? {
            return BCryptPasswordEncoder().encode(token)
        }
    }

    fun checkToken(rawToken: String): Boolean {
        return BCryptPasswordEncoder().matches(rawToken, this.token)
    }

    override fun toString(): String {
        return "RefreshToken(isRevoked=$isRevoked, user=${user.id}, expiresAt=$expiresAt, token='$token)"
    }
}