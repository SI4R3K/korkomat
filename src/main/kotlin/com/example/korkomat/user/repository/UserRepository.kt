package com.example.korkomat.user.repository

import com.example.korkomat.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
    fun findPasswordByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean

}
