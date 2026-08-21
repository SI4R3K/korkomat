package com.example.korkomat.user.repository

import com.example.korkomat.user.entity.StudentProfile
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StudentRepository: JpaRepository<StudentProfile, UUID> {
    fun existsByUserId(id: UUID): Boolean
    fun existsByUserEmail(email: String): Boolean
    fun findByUserEmail(email: String): StudentProfile?
}