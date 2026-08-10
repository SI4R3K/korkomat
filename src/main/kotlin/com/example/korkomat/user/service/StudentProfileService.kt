package com.example.korkomat.user.service

import com.example.korkomat.common.constant.Constant
import com.example.korkomat.user.dto.request.RegisterStudentRequest
import com.example.korkomat.user.dto.response.RegisterStudentResponse
import com.example.korkomat.user.entity.StudentProfile
import com.example.korkomat.user.excpetions.UserProfileAlreadyExistsException
import com.example.korkomat.user.excpetions.UserNotFoundException
import com.example.korkomat.user.repository.StudentRepository
import com.example.korkomat.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class StudentProfileService(
    private val userRepository: UserRepository,
    private val studentRepository: StudentRepository
) : UserProfileService<RegisterStudentRequest, RegisterStudentResponse> {

    override fun register(request: RegisterStudentRequest): RegisterStudentResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw UserNotFoundException(
                String.format(Constant.USER_NOT_FOUND, request.email)
            )

        if (studentRepository.existsByUserId(
                requireNotNull(user.id) { "User with id ${user.id} not found." })
        ) {
            throw UserProfileAlreadyExistsException(
                String.format(Constant.STUDENT_PROFILE_ALREADY_EXISTS, request.email)
            )
        }

        studentRepository.save(StudentProfile(user = user))
        return RegisterStudentResponse(
            "Student Profile created!",
        )
    }
}