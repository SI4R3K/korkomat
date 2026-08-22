package com.example.korkomat.subject.repository

import com.example.korkomat.subject.entity.Subject
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectRepository: JpaRepository<Subject, Long> {
    fun existsByName(name: String): Boolean
}