package com.example.korkomat.subject.dto.request

import com.example.korkomat.subject.entity.enumeration.SubjectLevel

data class CreateTutorSubjectRequest(
    val subjectId: Long,
    val level: SubjectLevel,
    val description: String? = null,
)
