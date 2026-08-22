package com.example.korkomat.slot.service

import com.example.korkomat.auth.service.CurrentProfileService
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.slot.dto.request.AvailableSlotFilterRequest
import com.example.korkomat.slot.dto.request.CreateAvailableSlotRequest
import com.example.korkomat.slot.dto.request.UpdateAvailableSlotRequest
import com.example.korkomat.slot.dto.response.AllAvailableSlotsResponse
import com.example.korkomat.slot.dto.response.AvailableSlotResponse
import com.example.korkomat.slot.dto.response.AvailableSlotsResponse
import com.example.korkomat.slot.dto.response.CreateAvailableSlotResponse
import com.example.korkomat.slot.dto.response.DeleteAvailableSlotsResponse
import com.example.korkomat.slot.dto.response.SearchAvailableSlotsResponse
import com.example.korkomat.slot.dto.response.UpdateAvailableSlotsResponse
import com.example.korkomat.slot.entity.AvailableSlot
import com.example.korkomat.slot.entity.enumeration.SlotStatus
import com.example.korkomat.slot.exceptions.AvailableSlotDoesNotExistException
import com.example.korkomat.slot.exceptions.InvalidSlotTimeException
import com.example.korkomat.slot.exceptions.SlotUnavailableException
import com.example.korkomat.slot.repository.AvailableSlotRepository
import com.example.korkomat.user.entity.TutorProfile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class AvailableSlotServiceImpl(
    private val availableSlotRepository: AvailableSlotRepository,
    private val currentProfileService: CurrentProfileService
) : AvailableSlotService {

    @Transactional
    override fun createAvailableSlot(request: CreateAvailableSlotRequest): CreateAvailableSlotResponse {
        val tutor = currentProfileService.getCurrentTutor()

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
        val tutor = currentProfileService.getCurrentTutor()
        val existingSlot = findAvailableSlotForTutor(id, tutor)

        val updatedStartTime = request.startTime ?: existingSlot.startTime
        val updatedEndTime = request.endTime ?: existingSlot.endTime
        val updatedType = request.type ?: existingSlot.type

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
        existingSlot.type = updatedType

        return UpdateAvailableSlotsResponse(
            message = "Slot updated successfully!",
            updatedAvailableSlot = existingSlot.toAvailableSlotResponse()
        )
    }

    @Transactional
    override fun deleteAvailableSlot(id: Long): DeleteAvailableSlotsResponse {
        val tutor = currentProfileService.getCurrentTutor()
        val existingSlot = findAvailableSlotForTutor(id, tutor)

        if (existingSlot.slotStatus != SlotStatus.AVAILABLE) {
            throw SlotUnavailableException(
                String.format(
                    Constant.SLOT_RESERVED, id
                )
            )
        }

        availableSlotRepository.delete(existingSlot)

        return DeleteAvailableSlotsResponse(
            message = "Slot deleted successfully!",
            existingSlot.toAvailableSlotResponse()
        )
    }

    @Transactional(readOnly = true)
    override fun getMyAvailableSlots(): AvailableSlotsResponse {
        val tutor = currentProfileService.getCurrentTutor()

        return AvailableSlotsResponse(
            availableSlotRepository
                .findByTutorProfileAndStartTimeGreaterThanEqual(tutor)
                .map { it.toAvailableSlotResponse() }
        )
    }

    @Transactional(readOnly = true)
    override fun searchForAvailableSlots(filter: AvailableSlotFilterRequest): AllAvailableSlotsResponse {
        currentProfileService.requireCurrentUserToBeStudent()

        return AllAvailableSlotsResponse(
            allAvailableSlots = availableSlotRepository
                .searchAvailableSlots(
                    fromTime = filter.fromTime ?: Instant.now(),
                    toTime = filter.toTime ?: Instant.now().plusSeconds(31536000), // additional year
                    slotStatus = SlotStatus.AVAILABLE,
                    tutorId = filter.tutorId,
                    lessonType = filter.lessonType
                ).map { it.toAllAvailableSlotResponse() }
        )
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
            lessonId = lesson?.id,
        )
    }

    private fun AvailableSlot.toAllAvailableSlotResponse(): SearchAvailableSlotsResponse {
        return SearchAvailableSlotsResponse(
            tutorName = tutorProfile?.user?.getFullName(),
            startTime = startTime,
            endTime = endTime,
            type = type,
        )
}

}
