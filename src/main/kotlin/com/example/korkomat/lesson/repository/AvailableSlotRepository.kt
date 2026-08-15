package com.example.korkomat.lesson.repository

import com.example.korkomat.lesson.entity.AvailableSlot
import com.example.korkomat.user.entity.TutorProfile
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

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
}
