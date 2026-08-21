package com.example.korkomat.lesson.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.lesson.dto.response.StudentTutorSubjectsResponse
import com.example.korkomat.lesson.service.TutorSubjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("subjects")
class StudentTutorSubjectController(
    private val tutorSubjectService: TutorSubjectService
) {

    @GetMapping("/{tutorId}")
    fun getTutorSubjects(
        @PathVariable tutorId: UUID
    ): ResponseEntity<Api<StudentTutorSubjectsResponse>> {
        val response = tutorSubjectService.getTutorSubjects(tutorId)

        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Subjects for tutor $tutorId retrieved successfully!"))
    }
}