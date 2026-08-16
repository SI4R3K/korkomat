package com.example.korkomat.lesson.repository

import com.example.korkomat.lesson.entity.AvailableSlot
import com.example.korkomat.user.entity.TutorProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.Instant
import java.util.UUID

interface AvailableSlotRepository: JpaRepository<AvailableSlot, Long> {
    fun existsByTutorProfileAndStartTimeLessThanAndEndTimeGreaterThan(
        tutorProfile: TutorProfile,
        endTime: Instant,
        startTime: Instant
    ): Boolean

    fun findByTutorProfileAndStartTimeGreaterThanEqual(
        tutorProfile: TutorProfile,
        timeGreaterThan: Instant = Instant.now()
    ): List<AvailableSlot>

    fun findByStartTimeGreaterThanEqual(
        timeGreaterThan: Instant = Instant.now()
    ) : List<AvailableSlot>

    fun findByIdAndTutorProfileId(id: Long, tutorId: UUID): AvailableSlot?

    @Query("""
    select case when count(slot) > 0 then true else false end
    from AvailableSlot slot
    where slot.tutorProfile = :tutorProfile
      and slot.id <> :excludedSlotId
      and slot.startTime < :endTime
      and slot.endTime > :startTime
""")
    fun existsOverlappingSlotExcludingId(
        tutorProfile: TutorProfile,
        excludedSlotId: Long,
        startTime: Instant,
        endTime: Instant,
    ): Boolean
}
