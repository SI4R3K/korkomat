package com.example.korkomat.lesson.dto.request

import com.example.korkomat.lesson.entity.enumeration.SubjectLevel

data class CreateTutorSubjectRequest(
    val subjectId: Long,
    val level: SubjectLevel,
    val description: String? = null,
)
