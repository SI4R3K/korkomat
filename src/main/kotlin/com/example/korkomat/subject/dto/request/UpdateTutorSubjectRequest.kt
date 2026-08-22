package com.example.korkomat.subject.dto.request

import com.example.korkomat.subject.entity.enumeration.SubjectLevel

data class UpdateTutorSubjectRequest(
    val subjectId: Long? = null,
    val level: SubjectLevel? = null,
    val description: String? = null,
)
