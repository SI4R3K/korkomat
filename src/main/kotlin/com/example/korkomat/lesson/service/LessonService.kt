package com.example.korkomat.lesson.service

import com.example.korkomat.lesson.dto.request.LessonFilterRequest
import com.example.korkomat.lesson.dto.response.GetLessonsResponse
import com.example.korkomat.lesson.dto.response.LessonResponse


interface LessonService {
    fun getLessonsForStudent(filter: LessonFilterRequest): GetLessonsResponse
    fun getLessonsForTutor(filter: LessonFilterRequest): GetLessonsResponse
    fun confirmLesson(lessonId: Long): LessonResponse
    fun rejectLesson(lessonId: Long): LessonResponse
}