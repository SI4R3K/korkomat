package com.example.korkomat.slot.repository

import com.example.korkomat.lesson.entity.enumeration.LessonType
import com.example.korkomat.slot.entity.AvailableSlot
import com.example.korkomat.slot.entity.enumeration.SlotStatus
import com.example.korkomat.user.entity.TutorProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
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

    @Query("""
    SELECT slot
    FROM AvailableSlot slot
    WHERE slot.startTime >= :fromTime
      AND slot.endTime <= :toTime
      AND slot.slotStatus = :slotStatus
      AND (:tutorId IS NULL OR slot.tutorProfile.id = :tutorId)
      AND (:lessonType IS NULL OR slot.type = :lessonType)
    """)
    fun searchAvailableSlots(
        @Param("fromTime") fromTime: Instant,
        @Param("toTime") toTime: Instant,
        @Param("slotStatus") slotStatus: SlotStatus,
        @Param("tutorId") tutorId: UUID?,
        @Param("lessonType") lessonType: LessonType?
    ): List<AvailableSlot>
}