package com.example.korkomat.auth.entity

import com.example.korkomat.auth.entity.enumeration.VerificationTokenType
import com.example.korkomat.user.entity.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.PreUpdate
import java.time.Instant

@Entity(name = "verification_tokens")
data class VerificationToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var token: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: VerificationTokenType,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(nullable = false, name = "user_id")
    val user: User,

    @Column(nullable = false)
    var expiresAt: Instant,

    var confirmedAt: Instant? = null,

    @Column(nullable = true)
    var usedAt: Instant? = null
    ) {

    @PreUpdate
    fun onUpdate() {
        confirmedAt = Instant.now()
    }

    override fun toString(): String {
        val maskedToken = token.replaceRange(
            0,
            token.length - 4,
            "*".repeat(token.length - 4)
        )
        return "ConfirmationToken(id=$id, token=$maskedToken, userId=${user.id}, " +
                "expiresAt=$expiresAt, confirmedAt=$confirmedAt, createdAt=$usedAt)"
    }
}