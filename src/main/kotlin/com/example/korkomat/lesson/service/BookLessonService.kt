package com.example.korkomat.lesson.service

import com.example.korkomat.lesson.dto.request.BookLessonRequest
import com.example.korkomat.lesson.dto.response.BookLessonResponse

interface BookLessonService {
    fun bookLesson(slotId: Long, request: BookLessonRequest): BookLessonResponse
}