package com.example.korkomat.lesson.dto.response

import com.example.korkomat.lesson.entity.enumeration.LessonStatus
import java.time.Instant

data class BookLessonResponse(
    val message: String,
    val startTime: Instant,
    val endTime: Instant,
    val tutorName: String?,
    val lessonStatus: LessonStatus,
)
