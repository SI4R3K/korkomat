package com.example.korkomat.lesson.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.lesson.dto.request.LessonFilterRequest
import com.example.korkomat.lesson.dto.response.GetLessonsResponse
import com.example.korkomat.lesson.dto.response.LessonResponse
import com.example.korkomat.lesson.service.LessonService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tutor/lessons")
class TutorLessonController(
    private val lessonService: LessonService,
) {
    @PatchMapping("/{lessonId}/confirm")
    fun confirmLesson(
        @PathVariable lessonId: Long
    ): ResponseEntity<Api<LessonResponse>> {
        val response = lessonService.confirmLesson(lessonId)
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Lesson confirmed!"))
    }

    @PatchMapping("/{lessonId}/reject")
    fun rejectLesson(
        @PathVariable lessonId: Long
    ): ResponseEntity<Api<LessonResponse>> {
        val response = lessonService.rejectLesson(lessonId)
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Lesson rejected!"))
    }

    @GetMapping
    fun getLessons(
        @ModelAttribute filter: LessonFilterRequest
    ): ResponseEntity<Api<GetLessonsResponse>> {
        val response = lessonService.getLessonsForTutor(filter)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Api.ok(response, "Lessons found"))
    }
}