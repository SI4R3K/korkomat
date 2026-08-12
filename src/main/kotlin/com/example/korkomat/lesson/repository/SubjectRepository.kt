package com.example.korkomat.lesson.repository

import com.example.korkomat.lesson.entity.Subject
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectRepository: JpaRepository<Subject, Long> {
    fun existsByName(name: String): Boolean
}