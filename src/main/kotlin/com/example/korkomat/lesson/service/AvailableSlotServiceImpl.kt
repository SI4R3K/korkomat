package com.example.korkomat.lesson.service

import com.example.korkomat.auth.exceptions.UnauthenticatedUserException
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.lesson.dto.request.CreateAvailableSlotRequest
import com.example.korkomat.lesson.dto.request.UpdateAvailableSlotRequest
import com.example.korkomat.lesson.dto.response.AvailableSlotResponse
import com.example.korkomat.lesson.dto.response.AvailableSlotsResponse
import com.example.korkomat.lesson.dto.response.CreateAvailableSlotResponse
import com.example.korkomat.lesson.dto.response.DeleteAvailableSlotsResponse
import com.example.korkomat.lesson.dto.response.UpdateAvailableSlotsResponse
import com.example.korkomat.lesson.entity.AvailableSlot
import com.example.korkomat.lesson.excpeptions.AvailableSlotDoesNotExistException
import com.example.korkomat.lesson.excpeptions.InvalidSlotTimeException
import com.example.korkomat.lesson.repository.AvailableSlotRepository
import com.example.korkomat.user.entity.TutorProfile
import com.example.korkomat.user.excpetions.InvalidProfileException
import com.example.korkomat.user.excpetions.UserNotFoundException
import com.example.korkomat.user.repository.StudentRepository
import com.example.korkomat.user.repository.TutorRepository
import com.example.korkomat.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class AvailableSlotServiceImpl(
    private val tutorRepository: TutorRepository,
    private val studentRepository: StudentRepository,
    private val userRepository: UserRepository,
    private val availableSlotRepository: AvailableSlotRepository,
) : AvailableSlotService {

    @Transactional
    override fun createAvailableSlot(request: CreateAvailableSlotRequest): CreateAvailableSlotResponse {
        val tutor = getCurrentTutor()

        isProposedTimeValid(request.startTime, request.endTime)

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

    @Transactional
    override fun updateAvailableSlot(
        id: Long,
        request: UpdateAvailableSlotRequest
    ): UpdateAvailableSlotsResponse {
        val tutor = getCurrentTutor()
        val existingSlot = findAvailableSlotForTutor(id, tutor)

        val updatedStartTime = request.startTime ?: existingSlot.startTime
        val updatedEndTime = request.endTime ?: existingSlot.endTime
        val upadtedType = request.type ?: existingSlot.type

        isProposedTimeValid(updatedStartTime, updatedEndTime)

        val hasConflict = availableSlotRepository
            .existsOverlappingSlotExcludingId(
                tutorProfile = tutor,
                excludedSlotId = id,
                startTime = updatedStartTime,
                endTime = updatedEndTime,
            )

        if (hasConflict) {
            throw InvalidSlotTimeException("Overlapping with existing slot is not allowed!")
        }

        existingSlot.startTime = updatedStartTime
        existingSlot.endTime = updatedEndTime
        existingSlot.type = upadtedType

        return UpdateAvailableSlotsResponse(
            message = "Slot updated successfully!",
            updatedAvailableSlot = existingSlot.toAvailableSlotResponse()
        )
    }

    @Transactional
    override fun deleteAvailableSlot(id: Long): DeleteAvailableSlotsResponse {
        val tutor = getCurrentTutor()
        val existingSlot = findAvailableSlotForTutor(id, tutor)
        availableSlotRepository.delete(existingSlot)
        return DeleteAvailableSlotsResponse(
            message = "Slot deleted successfully!",
            existingSlot.toAvailableSlotResponse()
        )
    }

    @Transactional(readOnly = true)
    override fun getMyAvailableSlots(): AvailableSlotsResponse {
        val tutor = getCurrentTutor()

        return AvailableSlotsResponse(
            availableSlotRepository
                .findByTutorProfileAndStartTimeGreaterThanEqual(tutor)
                .map { it.toAvailableSlotResponse() }
        )
    }

    @Transactional(readOnly = true)
    override fun getAllTutorsAvailableSlots(): AvailableSlotsResponse {
        if (!isStudent()) {
            throw InvalidProfileException(
                Constant.STUDENT_PROFILE_NOT_FOUND
            )
        }
        return AvailableSlotsResponse(
            availableSlotRepository
                .findByStartTimeGreaterThanEqual()
                .map { it.toAllAvailableSlotResponse() }
        )
    }

    private fun isStudent(): Boolean {
        val email = SecurityContextHolder.getContext().authentication?.name
            ?: throw UnauthenticatedUserException("Authenticated user not found in security context.")

        val user = userRepository.findByEmail(email)
            ?: throw UserNotFoundException("User not found.")

        return studentRepository.existsByUserId(user.id!!)
    }

    private fun getCurrentTutor(): TutorProfile {
        val email = SecurityContextHolder.getContext().authentication?.name
            ?: throw UnauthenticatedUserException("Authenticated user not found in security context.")

        return tutorRepository.findByUserEmail(email)
        ?: throw UserNotFoundException("Tutor profile for this [$email] does not exist.")
    }

    private fun isProposedTimeValid(startTime: Instant, endTime: Instant) {
        val currentTime = Instant.now()

        if (!startTime.isAfter(currentTime)) {
            throw InvalidSlotTimeException("Proposed slot must start in the future!")
        }

        if (!startTime.isBefore(endTime)) {
            throw InvalidSlotTimeException("Start time in [$startTime] must be before [$endTime]")
        }
    }

    private fun findAvailableSlotForTutor(id: Long, tutor: TutorProfile): AvailableSlot {
        return availableSlotRepository.findByIdAndTutorProfileId(id, requireNotNull(tutor.id))
            ?: throw AvailableSlotDoesNotExistException("Available slot with this [$id] does not exist.")
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

    private fun AvailableSlot.toAllAvailableSlotResponse(): AvailableSlotResponse {
        return AvailableSlotResponse(
            startTime = startTime,
            endTime = endTime,
            status = slotStatus,
            type = type,
            lesson = lesson,
            tutorId = tutorProfile?.id,
        )
}

}
