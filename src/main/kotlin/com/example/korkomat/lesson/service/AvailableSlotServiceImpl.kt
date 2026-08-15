package com.example.korkomat.lesson.service

import com.example.korkomat.auth.exceptions.UnauthenticatedUserException
import com.example.korkomat.lesson.dto.request.CreateAvailableSlotRequest
import com.example.korkomat.lesson.dto.response.AvailableSlotResponse
import com.example.korkomat.lesson.dto.response.AvailableSlotsResponse
import com.example.korkomat.lesson.dto.response.CreateAvailableSlotResponse
import com.example.korkomat.lesson.entity.AvailableSlot
import com.example.korkomat.lesson.excpeptions.InvalidSlotTimeException
import com.example.korkomat.lesson.repository.AvailableSlotRepository
import com.example.korkomat.user.entity.TutorProfile
import com.example.korkomat.user.excpetions.UserNotFoundException
import com.example.korkomat.user.repository.TutorRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class AvailableSlotServiceImpl(
    private val tutorRepository: TutorRepository,
    private val availableSlotRepository: AvailableSlotRepository,
) : AvailableSlotService {

    @Transactional
    override fun createAvailableSlot(request: CreateAvailableSlotRequest): CreateAvailableSlotResponse {
        val tutor = getCurrentTutor()

        isProposedTimeValid(request)

        val hasConflict =
            availableSlotRepository
                .existsByTutorProfileAndStartTimeLessThanAndEndTimeGreaterThan(
                    tutorProfile = tutor,
                    endTime = request.endTime,
                    startTime = request.startTime,
                )

        if (hasConflict) {
            throw InvalidSlotTimeException(
                "Overlapping with existing slot is not allowed!"
            )
        }

        availableSlotRepository.save(
            AvailableSlot(
                tutorProfile = tutor,
                type = request.type,
                startTime = request.startTime,
                endTime = request.endTime,
            )
        )
        return CreateAvailableSlotResponse(
            message = "Slot created successfully!"
        )
    }

    @Transactional(readOnly = true)
    override fun getAvailableSlots(): AvailableSlotsResponse {
        val tutor = getCurrentTutor()

        return AvailableSlotsResponse(
            availableSlotRepository
                .findByTutorProfileAndStartTimeGreaterThanEqual(tutor)
                .map { it.toAvailableSlotResponse() }
        )
    }

    private fun getCurrentTutor(): TutorProfile {
        val email = SecurityContextHolder.getContext().authentication?.name
            ?: throw UnauthenticatedUserException("Authenticated user not found in security context.")

        return tutorRepository.findByUserEmail(email)
        ?: throw UserNotFoundException("Tutor profile for this [$email] does not exist.")
    }

    private fun isProposedTimeValid(request: CreateAvailableSlotRequest) {
        val currentTime = Instant.now()
        val startTime = request.startTime
        val endTime = request.endTime

        if (!startTime.isAfter(currentTime)) {
            throw InvalidSlotTimeException("Proposed slot must start in the future!")
        }

        if (!startTime.isBefore(endTime)) {
            throw InvalidSlotTimeException("Start time in [$startTime] must be before [$endTime]")
        }
    }

    private fun AvailableSlot.toAvailableSlotResponse(): AvailableSlotResponse {
        return AvailableSlotResponse(
            startTime = startTime,
            endTime = endTime,
            status = slotStatus,
            type = type,
            lesson = lesson,
        )
    }

}
