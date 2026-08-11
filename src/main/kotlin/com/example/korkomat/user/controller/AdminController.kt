package com.example.korkomat.user.controller

import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.user.dto.response.AdminGetStudentResponse
import com.example.korkomat.user.dto.response.AdminGetStudentsResponse
import com.example.korkomat.user.dto.response.AdminGetTutorResponse
import com.example.korkomat.user.dto.response.AdminGetTutorsResponse
import com.example.korkomat.user.dto.response.AdminGetUserResponse
import com.example.korkomat.user.dto.response.AdminGetUsersResponse
import com.example.korkomat.user.service.AdminService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("admin")
class AdminController(
    val adminService: AdminService,
) {

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<Api<AdminGetUsersResponse>> {
        val response = adminService.getUsers()
        val successResponse = Api.ok(response, "Users fetched successfully.")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<Api<AdminGetUserResponse>> {
        val response = adminService.getUser(id)
        val successResponse = Api.ok(response, "User fetched successfully.")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @GetMapping("/students")
    fun getStudents(): ResponseEntity<Api<AdminGetStudentsResponse>> {
        val response = adminService.getStudents()
        val successResponse = Api.ok(response, "Students fetched successfully.")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @GetMapping("/students/{id}")
    fun getStudent(@PathVariable id: String): ResponseEntity<Api<AdminGetStudentResponse>> {
        val response = adminService.getStudent(id)
        val successResponse = Api.ok(response, "Student fetched successfully.")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @GetMapping("/tutors")
    fun getTutors(): ResponseEntity<Api<AdminGetTutorsResponse>> {
        val response = adminService.getTutors()
        val successResponse = Api.ok(response, "Tutors fetched successfully.")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }

    @GetMapping("/tutors/{id}")
    fun getTutor(@PathVariable id: String): ResponseEntity<Api<AdminGetTutorResponse>> {
        val response = adminService.getTutor(id)
        val successResponse = Api.ok(response, "Tutor fetched successfully.")
        return ResponseEntity.status(HttpStatus.OK).body(successResponse)
    }
}
