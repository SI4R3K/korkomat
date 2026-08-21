package com.example.korkomat.user.service

import com.example.korkomat.auth.exceptions.UnauthenticatedUserException
import com.example.korkomat.auth.service.CurrentUserProvider
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.user.dto.request.RegisterTutorRequest
import com.example.korkomat.user.dto.response.RegisterTutorResponse
import com.example.korkomat.user.entity.TutorProfile
import com.example.korkomat.user.excpetions.UserProfileAlreadyExistsException
import com.example.korkomat.user.excpetions.UserNotFoundException
import com.example.korkomat.user.repository.TutorRepository
import com.example.korkomat.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class TutorProfileService(
    private val userRepository: UserRepository,
    private val tutorRepository: TutorRepository,
    private val currentUserProvider: CurrentUserProvider
): UserProfileService<RegisterTutorRequest, RegisterTutorResponse> {

    override fun register(request: RegisterTutorRequest): RegisterTutorResponse {
        val user = currentUserProvider.getCurrentUser()

        if (tutorRepository.existsByUserId(
                requireNotNull(user.id) { "User with id ${user.id} not found." })
        ) {
            throw UserProfileAlreadyExistsException(
                String.format(Constant.TUTOR_PROFILE_ALREADY_EXISTS, user.email)
            )
        }

        tutorRepository.save(
            TutorProfile(
                bio = request.bio,
                hourlyRate = request.hourlyRate,
                user = user
            )
        )
        return RegisterTutorResponse(
            "Tutor profile created!",
        )
    }

}