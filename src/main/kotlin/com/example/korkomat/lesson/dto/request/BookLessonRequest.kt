package com.example.korkomat.lesson.dto.request

data class BookLessonRequest(
    val tutorSubjectId: Long,
    val place: String?
)