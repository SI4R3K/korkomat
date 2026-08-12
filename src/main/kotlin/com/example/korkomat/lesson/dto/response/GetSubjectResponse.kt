package com.example.korkomat.lesson.dto.response

import java.util.UUID


data class GetSubjectsResponse(
    val subjects: List<SubjectResponse>
)

data class GetSubjectResponse(
    val subject: SubjectResponse
)

data class SubjectResponse(
    val id: Long?,
    val name: String
)


