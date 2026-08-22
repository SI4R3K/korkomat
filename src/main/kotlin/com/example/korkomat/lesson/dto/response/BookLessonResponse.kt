package com.example.korkomat.lesson.dto.response

import com.example.korkomat.lesson.entity.enumeration.LessonStatus
import com.example.korkomat.subject.entity.enumeration.SubjectLevel
import java.time.Instant

data class BookLessonResponse(
    val message: String,
    val subjectName: String,
    val level: SubjectLevel,
    val startTime: Instant,
    val endTime: Instant,
    val tutorName: String?,
    val lessonStatus: LessonStatus,
)