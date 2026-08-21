package com.example.korkomat.lesson.dto.request

import com.example.korkomat.lesson.entity.enumeration.SubjectLevel

data class BookLessonRequest(
    val tutorSubjectId: Long,
    val place: String?
)
