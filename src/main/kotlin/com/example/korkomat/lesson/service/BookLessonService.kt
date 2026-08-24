package com.example.korkomat.lesson.service

import com.example.korkomat.lesson.dto.request.BookLessonRequest
import com.example.korkomat.lesson.dto.response.BookLessonResponse
import com.example.korkomat.lesson.dto.response.CancelLessonResponse
import com.example.korkomat.lesson.dto.response.LessonResponse
import com.example.korkomat.lesson.dto.response.StudentLessonResponse

interface BookLessonService {
    fun reserveLesson(slotId: Long, request: BookLessonRequest): BookLessonResponse
    fun cancelReservation(id: Long): CancelLessonResponse
}