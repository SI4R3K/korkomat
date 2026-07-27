package com.example.korkomat.user.repository

import com.example.korkomat.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
    fun findPasswordByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean
}
