package com.example.korkomat.user.service

import com.example.korkomat.auth.service.CurrentUserProvider
import com.example.korkomat.user.dto.request.EditMyProfileRequest
import com.example.korkomat.user.dto.request.RegisterStudentRequest
import com.example.korkomat.user.dto.request.RegisterTutorRequest
import com.example.korkomat.user.dto.response.RegisterStudentResponse
import com.example.korkomat.user.dto.response.RegisterTutorResponse
import com.example.korkomat.user.dto.response.StudentProfileResponse
import com.example.korkomat.user.dto.response.TutorProfileResponse
import com.example.korkomat.user.dto.response.UserInfoResponse
import com.example.korkomat.user.repository.StudentRepository
import com.example.korkomat.user.repository.TutorRepository
import com.example.korkomat.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val studentProfileService: StudentProfileServiceImpl,
    private val tutorProfileService: TutorProfileServiceImpl,
    private val currentUserProvider: CurrentUserProvider,
    private val studentRepository: StudentRepository,
    private val tutorRepository: TutorRepository,
    private val userRepository: UserRepository
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
            phoneNumber = user.phoneNumber,
            location = user.location,
            fullName = user.getFullName(),

            studentProfile = student?.let {
                StudentProfileResponse(
                    id = it.id
                )
            },

            tutorProfile = tutor?.let {
                TutorProfileResponse(
                    id = it.id,
                    bio = it.bio,
                    hourlyRate = it.hourlyRate
                )
            }
        )
    }

    @Transactional
    override fun editCurrentUserInfo(request: EditMyProfileRequest) {
        val user = currentUserProvider.getCurrentUser()

        request.newFirstName?.let { user.firstName = it }
        request.newLastName?.let { user.lastName = it }
        request.newPhoneNumber?.let { user.phoneNumber = it }
        request.newLocation?.let { user.location = it }

        userRepository.save(user)
    }
}