package com.example.korkomat.user.service

import com.example.korkomat.user.dto.response.AdminGetStudentResponse
import com.example.korkomat.user.dto.response.AdminGetStudentsResponse
import com.example.korkomat.user.dto.response.AdminGetTutorResponse
import com.example.korkomat.user.dto.response.AdminGetTutorsResponse
import com.example.korkomat.user.dto.response.AdminGetUserResponse
import com.example.korkomat.user.dto.response.AdminGetUsersResponse

interface AdminService {
    fun getUsers(): AdminGetUsersResponse
    fun getUser(id: String): AdminGetUserResponse
    fun getStudents(): AdminGetStudentsResponse
    fun getStudent(id: String): AdminGetStudentResponse
    fun getTutors(): AdminGetTutorsResponse
    fun getTutor(id: String): AdminGetTutorResponse
}
