package com.example.korkomat.user.service

import com.example.korkomat.user.dto.request.EditTutorProfileRequest
import com.example.korkomat.user.dto.response.TutorProfileResponse
import java.util.UUID

interface TutorProfileService {
    fun editProfile(request: EditTutorProfileRequest)
    fun getMyTutorProfile(): TutorProfileResponse
    fun getTutorProfile(tutorProfileId: UUID): TutorProfileResponse
}