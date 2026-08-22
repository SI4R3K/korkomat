package com.example.korkomat.subject.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.subject.dto.request.CreateSubjectRequest
import com.example.korkomat.subject.dto.request.UpdateSubjectRequest
import com.example.korkomat.subject.dto.response.CreateSubjectResponse
import com.example.korkomat.subject.dto.response.DeleteSubjectResponse
import com.example.korkomat.subject.dto.response.GetSubjectResponse
import com.example.korkomat.subject.dto.response.GetSubjectsResponse
import com.example.korkomat.subject.dto.response.UpdateSubjectResponse
import com.example.korkomat.subject.service.SubjectAdminService
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
@RequestMapping("/admin/subjects")
class AdminSubjectController(
    private val subjectAdminService: SubjectAdminService,
) {

    @PostMapping
    fun createSubject(@RequestBody request: CreateSubjectRequest): ResponseEntity<Api<CreateSubjectResponse>> {
        val response = subjectAdminService.createSubject(request)
        val successResponse = Api.ok(response, "Subject created successfully!")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @GetMapping
    fun getSubjects(): ResponseEntity<Api<GetSubjectsResponse>> {
        val response = subjectAdminService.getSubjects()
        val successResponse = Api.ok(response, "All subjects retrieved successfully!")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @GetMapping("/{id}")
    fun getSubject(@PathVariable id: String): ResponseEntity<Api<GetSubjectResponse>> {
        val response = subjectAdminService.getSubject(id)
        val successResponse = Api.ok(response, "Subject retrieved successfully!")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @PutMapping("/{id}")
    fun updateSubject(
        @PathVariable id: String,
        @RequestBody request: UpdateSubjectRequest
    ): ResponseEntity<Api<UpdateSubjectResponse>> {
        val response = subjectAdminService.updateSubject(id, request)
        val successResponse = Api.ok(response, "Subject updated successfully!")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @DeleteMapping("/{id}")
    fun deleteSubject(@PathVariable id: String): ResponseEntity<Api<DeleteSubjectResponse>> {
        val response = subjectAdminService.deleteSubject(id)
        val successResponse = Api.ok(response, "Subject deleted successfully!")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

}