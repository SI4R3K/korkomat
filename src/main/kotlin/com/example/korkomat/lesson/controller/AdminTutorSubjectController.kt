package com.example.korkomat.lesson.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.lesson.dto.response.TutorSubjectDetailsResponse
import com.example.korkomat.lesson.dto.response.TutorSubjectsResponse
import com.example.korkomat.lesson.service.AdminTutorSubjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/admin/tutor-subjects")
class AdminTutorSubjectController(
    private val adminTutorSubjectService: AdminTutorSubjectService,
) {

    @GetMapping
    fun searchTutorSubjects(
        @RequestParam(required = false) id: Long?,
        @RequestParam(required = false) subjectName: String?,
        @RequestParam(required = false) tutorId: UUID?,
        @RequestParam(required = false) tutorEmail: String?,
    ): ResponseEntity<Api<TutorSubjectsResponse>> {
        val response = adminTutorSubjectService.searchTutorSubjects(
            id = id,
            subjectName = subjectName,
            tutorId = tutorId,
            tutorEmail = tutorEmail,
        )
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Tutor subjects retrieved successfully!"))
    }

    @GetMapping("/{id}")
    fun getTutorSubject(
        @PathVariable id: Long
    ): ResponseEntity<Api<TutorSubjectDetailsResponse>> {
        val response = adminTutorSubjectService.getTutorSubject(id)
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Tutor subject retrieved successfully!"))
    }
}
