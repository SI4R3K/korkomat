package com.example.korkomat.auth.entity

import com.example.korkomat.user.entity.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.PreUpdate
import java.time.Instant

@Entity(name = "confirmation_tokens")
data class ConfirmationToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var token: String,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(nullable = false, name = "user_id")
    val user: User,

    @Column(nullable = false)
    var expiresAt: Instant,

    var confirmedAt: Instant? = null,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()
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
                "expiresAt=$expiresAt, confirmedAt=$confirmedAt, createdAt=$createdAt)"
    }
}