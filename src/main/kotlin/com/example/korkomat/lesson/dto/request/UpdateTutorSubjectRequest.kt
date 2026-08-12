package com.example.korkomat.lesson.dto.request

import com.example.korkomat.lesson.entity.SubjectLevel

data class UpdateTutorSubjectRequest(
    val subjectId: Long? = null,
    val level: SubjectLevel? = null,
    val description: String? = null,
)
