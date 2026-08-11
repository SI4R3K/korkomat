package com.example.korkomat.user.service

import com.example.korkomat.user.dto.request.RegisterStudentRequest
import com.example.korkomat.user.dto.request.RegisterTutorRequest
import com.example.korkomat.user.dto.response.RegisterStudentResponse
import com.example.korkomat.user.dto.response.RegisterTutorResponse
import com.example.korkomat.user.entity.TutorProfile
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val studentProfileService: StudentProfileService,
    private val tutorProfileService: TutorProfileService,
    ): UserService {

    override fun registerTutor(request: RegisterTutorRequest): RegisterTutorResponse {
        return tutorProfileService.register(request)
    }

    override fun registerStudent(request: RegisterStudentRequest): RegisterStudentResponse {
        return studentProfileService.register(request)
    }

}