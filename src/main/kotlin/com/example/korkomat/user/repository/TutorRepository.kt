package com.example.korkomat.user.repository

import com.example.korkomat.user.entity.TutorProfile
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TutorRepository: JpaRepository<TutorProfile, UUID> {
    fun existsByUserId(id: UUID): Boolean
    fun existsByUserEmail(email: String): Boolean
    fun findByUserEmail(email: String): TutorProfile?
}
