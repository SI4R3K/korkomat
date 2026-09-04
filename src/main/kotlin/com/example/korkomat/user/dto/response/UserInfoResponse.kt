package com.example.korkomat.user.dto.response

import java.util.UUID

data class UserInfoResponse(
    val id: UUID?,
    val email: String,
    val fullName: String,
    val studentProfile: StudentProfileResponse? = null,
    val tutorProfile: TutorProfileResponse? = null,
)

data class StudentProfileResponse(
    val id: UUID?,
)

data class TutorProfileResponse(
    val id: UUID?,
)
