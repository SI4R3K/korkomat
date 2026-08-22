package com.example.korkomat.lesson.dto.request

import com.example.korkomat.lesson.entity.enumeration.LessonStatus

data class LessonFilterRequest(
    val status: LessonStatus
)
