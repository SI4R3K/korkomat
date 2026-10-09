package com.example.korkomat.user.service

import com.example.korkomat.auth.service.CurrentProfileService
import com.example.korkomat.auth.service.CurrentUserProvider
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.user.dto.request.EditTutorProfileRequest
import com.example.korkomat.user.dto.request.RegisterTutorRequest
import com.example.korkomat.user.dto.response.RegisterTutorResponse
import com.example.korkomat.user.dto.response.TutorProfileResponse
import com.example.korkomat.user.entity.TutorProfile
import com.example.korkomat.user.excpetions.UserNotFoundException
import com.example.korkomat.user.excpetions.UserProfileAlreadyExistsException
import com.example.korkomat.user.repository.TutorRepository
import com.example.korkomat.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class TutorProfileServiceImpl(
    private val userRepository: UserRepository,
    private val tutorRepository: TutorRepository,
    private val currentUserProvider: CurrentUserProvider,
    private val currentProfileService: CurrentProfileService,
): UserProfileService<RegisterTutorRequest, RegisterTutorResponse>, TutorProfileService {

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

    @Transactional
    override fun editProfile(request: EditTutorProfileRequest) {
        val tutor = currentProfileService.getCurrentTutor()

        request.newBio?.let { tutor.bio = it}
        request.newHourlyRate?.let { tutor.hourlyRate = it }

        tutorRepository.save(tutor) // no needed because of Transactional annotation
    }

    override fun getMyTutorProfile(): TutorProfileResponse {
        val tutor = currentProfileService.getCurrentTutor()

        return TutorProfileResponse(
            tutor.id,
            tutor.bio,
            tutor.hourlyRate,
        )
    }

    override fun getTutorProfile(tutorProfileId: UUID): TutorProfileResponse {
        val tutor = tutorRepository.findById(tutorProfileId)
            .orElseThrow { throw UserNotFoundException(
                Constant.TUTOR_PROFILE_NOT_FOUND
            ) }

        return TutorProfileResponse(
            id = tutor.id,
            bio = tutor.bio,
            hourlyRate = tutor.hourlyRate,
        )
    }
}