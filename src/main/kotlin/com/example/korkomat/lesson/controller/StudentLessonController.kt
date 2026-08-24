package com.example.korkomat.lesson.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.lesson.dto.request.BookLessonRequest
import com.example.korkomat.lesson.dto.request.LessonFilterRequest
import com.example.korkomat.lesson.dto.response.BookLessonResponse
import com.example.korkomat.lesson.dto.response.CancelLessonResponse
import com.example.korkomat.lesson.dto.response.GetLessonsResponse
import com.example.korkomat.lesson.dto.response.LessonResponse
import com.example.korkomat.lesson.dto.response.StudentLessonResponse
import com.example.korkomat.lesson.service.BookLessonService
import com.example.korkomat.lesson.service.LessonService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/student/lessons")
class StudentLessonController(
    private val bookLessonService: BookLessonService,
    private val lessonService: LessonService
) {
    @PostMapping("/{slotId}")
    fun bookLesson(
        @PathVariable slotId: Long,
        @RequestBody request: BookLessonRequest,
        ): ResponseEntity<Api<BookLessonResponse>> {
        val response = bookLessonService.reserveLesson(slotId, request)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                Api.ok(
                    response,
                    "Slot booked, waiting for tutors confirmation."
                )
            )
    }

    @PostMapping("cancel/{lessonId}")
    fun cancelReservation(
        @PathVariable lessonId: Long,
    ): ResponseEntity<Api<CancelLessonResponse>> {
        val response = bookLessonService.cancelReservation(lessonId)
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Cancelled lesson reservation"))
    }

    @GetMapping
    fun getLessons(
        @ModelAttribute filter: LessonFilterRequest
    ): ResponseEntity<Api<GetLessonsResponse>> {
        val response = lessonService.getLessonsForStudent(filter)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Api.ok(response, "Lessons found"))
    }


}