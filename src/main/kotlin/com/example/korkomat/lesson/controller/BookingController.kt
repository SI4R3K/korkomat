package com.example.korkomat.lesson.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.lesson.dto.response.BookLessonResponse
import com.example.korkomat.lesson.service.BookLessonService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/lessons")
class BookingController(
    private val bookLessonService: BookLessonService
) {

    @PostMapping("/{slotId}")
    fun bookLesson(@PathVariable slotId: Long): ResponseEntity<Api<BookLessonResponse>> {
        val response = bookLessonService.bookLesson(slotId)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Api.ok(response, "Slot booked, waiting for tutors confirmation."))
    }


}