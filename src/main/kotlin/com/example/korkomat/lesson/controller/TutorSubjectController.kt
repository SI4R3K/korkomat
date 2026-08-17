package com.example.korkomat.lesson.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.lesson.dto.request.CreateTutorSubjectRequest
import com.example.korkomat.lesson.dto.request.UpdateTutorSubjectRequest
import com.example.korkomat.lesson.dto.response.CreateTutorSubjectResponse
import com.example.korkomat.lesson.dto.response.DeleteTutorSubjectResponse
import com.example.korkomat.lesson.dto.response.TutorSubjectDetailsResponse
import com.example.korkomat.lesson.dto.response.TutorSubjectsResponse
import com.example.korkomat.lesson.dto.response.UpdateTutorSubjectResponse
import com.example.korkomat.lesson.service.TutorSubjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tutor/subjects")
class TutorSubjectController(
    private val tutorSubjectService: TutorSubjectService,
) {

    @PostMapping
    fun createTutorSubject(
        @RequestBody request: CreateTutorSubjectRequest
    ): ResponseEntity<Api<CreateTutorSubjectResponse>> {
        val response = tutorSubjectService.createTutorSubject(request)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Api.ok(response, "Tutor subject created successfully!"))
    }



    @GetMapping
    fun getMyTutorSubjects(): ResponseEntity<Api<TutorSubjectsResponse>> {
        val response = tutorSubjectService.getMyTutorSubjects()
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Tutor subjects retrieved successfully!"))
    }

    @GetMapping("/{id}")
    fun getMyTutorSubject(
        @PathVariable id: Long
    ): ResponseEntity<Api<TutorSubjectDetailsResponse>> {
        val response = tutorSubjectService.getMyTutorSubject(id)
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Tutor subject retrieved successfully!"))
    }

    @PutMapping("/{id}")
    fun updateTutorSubject(
        @PathVariable id: Long,
        @RequestBody request: UpdateTutorSubjectRequest,
    ): ResponseEntity<Api<UpdateTutorSubjectResponse>> {
        val response = tutorSubjectService.updateTutorSubject(id, request)
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Tutor subject updated successfully!"))
    }

    @DeleteMapping("/{id}")
    fun deleteTutorSubject(
        @PathVariable id: Long
    ): ResponseEntity<Api<DeleteTutorSubjectResponse>> {
        val response = tutorSubjectService.deleteTutorSubject(id)
        return ResponseEntity.status(HttpStatus.OK)
            .body(Api.ok(response, "Tutor subject deleted successfully!"))
    }
}
