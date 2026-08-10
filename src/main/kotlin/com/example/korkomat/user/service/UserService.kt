package com.example.korkomat.user.service

import com.example.korkomat.user.dto.request.RegisterStudentRequest
import com.example.korkomat.user.dto.request.RegisterTutorRequest
import com.example.korkomat.user.dto.response.RegisterStudentResponse
import com.example.korkomat.user.dto.response.RegisterTutorResponse

interface UserService {
    fun registerTutor(request: RegisterTutorRequest): RegisterTutorResponse
    fun registerStudent(request: RegisterStudentRequest): RegisterStudentResponse
}