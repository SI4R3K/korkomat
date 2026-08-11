package com.example.korkomat.lesson.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.lesson.dto.request.CreateSubjectRequest
import com.example.korkomat.lesson.dto.request.UpdateSubjectRequest
import com.example.korkomat.lesson.dto.response.CreateSubjectResponse
import com.example.korkomat.lesson.dto.response.DeleteSubjectResponse
import com.example.korkomat.lesson.dto.response.GetSubjectResponse
import com.example.korkomat.lesson.dto.response.GetSubjectsResponse
import com.example.korkomat.lesson.dto.response.UpdateSubjectResponse
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
@RequestMapping("/admin/subjects")
class SubjectAdminController {

    @PostMapping
    fun createSubject(@RequestBody request: CreateSubjectRequest): ResponseEntity<Api<CreateSubjectResponse>> {
        TODO()
    }

    @GetMapping
    fun getSubjects(): ResponseEntity<Api<GetSubjectsResponse>> {
        TODO()
    }

    @GetMapping("/{id}")
    fun getSubject(@PathVariable id: Long): ResponseEntity<Api<GetSubjectResponse>> {
        TODO()
    }

    @PutMapping("/{id}")
    fun updateSubject(
        @PathVariable id: Long,
        @RequestBody request: UpdateSubjectRequest
    ): ResponseEntity<Api<UpdateSubjectResponse>> {
        TODO()
    }

    @DeleteMapping("/{id}")
    fun deleteSubject(@PathVariable id: Long): ResponseEntity<Api<DeleteSubjectResponse>> {
        TODO()
    }

}