package com.example.korkomat.auth.service

import com.example.korkomat.common.constant.Constant
import com.example.korkomat.user.entity.StudentProfile
import com.example.korkomat.user.entity.TutorProfile
import com.example.korkomat.user.excpetions.UserNotFoundException
import com.example.korkomat.user.repository.StudentRepository
import com.example.korkomat.user.repository.TutorRepository
import org.springframework.stereotype.Service

@Service
class CurrentProfileService(
    private val currentUserProvider: CurrentUserProvider,
    private val studentRepository: StudentRepository,
    private val tutorRepository: TutorRepository,
) {
    fun getCurrentTutor(): TutorProfile {
        val email = currentUserProvider.getCurrentUser().email

        return tutorRepository.findByUserEmail(email)
            ?: throw UserNotFoundException(
                Constant.TUTOR_PROFILE_NOT_FOUND
            )
    }

    fun getCurrentStudent(): StudentProfile {
        val email = currentUserProvider.getCurrentUser().email

        return studentRepository.findByUserEmail(email)
            ?: throw UserNotFoundException(
                Constant.STUDENT_PROFILE_NOT_FOUND
            )
    }

    fun requireCurrentUserToBeStudent() {
        val email = currentUserProvider
            .getCurrentUser()
            .email

        if (!studentRepository.existsByUserEmail(email)) {
            throw UserNotFoundException(
                Constant.STUDENT_PROFILE_NOT_FOUND
            )
        }
    }

    fun requireCurrentUserToBeTutor() {
        val email = currentUserProvider
            .getCurrentUser()
            .email

        if (!tutorRepository.existsByUserEmail(email)) {
            throw UserNotFoundException(
                Constant.TUTOR_PROFILE_NOT_FOUND
            )
        }
    }
}