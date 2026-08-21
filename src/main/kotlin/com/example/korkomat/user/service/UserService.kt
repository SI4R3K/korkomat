package com.example.korkomat.user.service

import com.example.korkomat.auth.exceptions.UnauthenticatedUserException
import com.example.korkomat.user.dto.request.RegisterStudentRequest
import com.example.korkomat.user.dto.request.RegisterTutorRequest
import com.example.korkomat.user.dto.response.RegisterStudentResponse
import com.example.korkomat.user.dto.response.RegisterTutorResponse
import com.example.korkomat.user.entity.TutorProfile
import com.example.korkomat.user.excpetions.UserNotFoundException
import com.example.korkomat.user.repository.StudentRepository
import com.example.korkomat.user.repository.TutorRepository
import com.example.korkomat.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder

interface UserService {
    fun registerTutor(request: RegisterTutorRequest): RegisterTutorResponse
    fun registerStudent(request: RegisterStudentRequest): RegisterStudentResponse
}