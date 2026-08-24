package com.example.korkomat.seed.dto

import com.example.korkomat.lesson.entity.enumeration.LessonStatus
import com.example.korkomat.lesson.entity.enumeration.LessonType
import com.example.korkomat.subject.entity.enumeration.SubjectLevel
import java.math.BigDecimal
import java.time.Instant

data class SeedData(
    val subjects: List<SeedSubject> = emptyList(),
    val students: List<SeedStudent> = emptyList(),
    val tutors: List<SeedTutor> = emptyList(),
    val lessons: List<SeedLesson> = emptyList(),
)

data class SeedSubject(
    val name: String,
)

data class SeedStudent(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
)

data class SeedTutor(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,

    val bio: String,
    val hourlyRate: BigDecimal,

    val subjects: List<SeedTutorSubject> = emptyList(),
    val slots: List<SeedAvailableSlot> = emptyList(),
)

data class SeedTutorSubject(
    val name: String,
    val level: SubjectLevel,
    val description: String? = null,
)

data class SeedAvailableSlot(
    val startTime: Instant,
    val endTime: Instant,
    val type: LessonType,
)

data class SeedLesson(
    val studentEmail: String,
    val tutorEmail: String,

    val subjectName: String,
    val level: SubjectLevel,

    val startTime: Instant,
    val endTime: Instant,

    val status: LessonStatus,
    val place: String? = null,
)