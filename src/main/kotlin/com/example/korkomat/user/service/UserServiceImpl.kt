package com.example.korkomat.user.service

import com.example.korkomat.auth.service.CurrentProfileService
import com.example.korkomat.auth.service.CurrentUserProvider
import com.example.korkomat.user.dto.request.RegisterStudentRequest
import com.example.korkomat.user.dto.request.RegisterTutorRequest
import com.example.korkomat.user.dto.response.RegisterStudentResponse
import com.example.korkomat.user.dto.response.RegisterTutorResponse
import com.example.korkomat.user.dto.response.StudentProfileResponse
import com.example.korkomat.user.dto.response.TutorProfileResponse
import com.example.korkomat.user.dto.response.UserInfoResponse
import com.example.korkomat.user.repository.StudentRepository
import com.example.korkomat.user.repository.TutorRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val studentProfileService: StudentProfileService,
    private val tutorProfileService: TutorProfileService,
    private val currentUserProvider: CurrentUserProvider,
    private val studentRepository: StudentRepository,
    private val tutorRepository: TutorRepository
    ): UserService {

    override fun registerTutor(request: RegisterTutorRequest): RegisterTutorResponse {
        return tutorProfileService.register(request)
    }

    override fun registerStudent(request: RegisterStudentRequest): RegisterStudentResponse {
        return studentProfileService.register(request)
    }

    override fun getCurrentUserInfo(): UserInfoResponse {
        val user = currentUserProvider.getCurrentUser()

        val student =
            studentRepository.findByUserEmail(user.email)

        val tutor =
            tutorRepository.findByUserEmail(user.email)

        return UserInfoResponse(
            id = user.id,
            email = user.email,
            fullName = user.getFullName(),

            studentProfile = student?.let {
                StudentProfileResponse(
                    id = it.id
                )
            },

            tutorProfile = tutor?.let {
                TutorProfileResponse(
                    id = it.id
                )
            }
        )
    }

}