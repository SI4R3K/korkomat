package com.example.korkomat.user.dto.response

import com.example.korkomat.auth.authorization.Role
import com.example.korkomat.subject.entity.enumeration.SubjectLevel
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class AdminGetUsersResponse(
    val users: List<AdminUserResponse>
)

data class AdminGetUserResponse(
    val user: AdminUserResponse
)

data class AdminGetStudentsResponse(
    val students: List<AdminStudentResponse>
)

data class AdminGetStudentResponse(
    val student: AdminStudentResponse
)

data class AdminGetTutorsResponse(
    val tutors: List<AdminTutorResponse>
)

data class AdminGetTutorResponse(
    val tutor: AdminTutorResponse
)

data class AdminUserResponse(
    val id: UUID?,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: Role,
    val isActive: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class AdminStudentResponse(
    val id: UUID?,
    val user: AdminUserResponse
)

data class AdminTutorResponse(
    val id: UUID?,
    val bio: String,
    val hourlyRate: BigDecimal,
    val tutorSubjects: List<AdminTutorSubjectResponse>,
    val user: AdminUserResponse
)

data class AdminTutorSubjectResponse(
    val id: Long?,
    val subject: String,
    val level: SubjectLevel,
    val description: String?
)
