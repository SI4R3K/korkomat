package com.example.korkomat.slot.entity

import com.example.korkomat.common.constant.Constant
import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.lesson.entity.enumeration.LessonType
import com.example.korkomat.slot.entity.enumeration.SlotStatus
import com.example.korkomat.slot.exceptions.InvalidSlotStatusException
import com.example.korkomat.slot.exceptions.SlotUnavailableException
import com.example.korkomat.user.entity.TutorProfile
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "available_slots")
data class AvailableSlot(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "tutor_profile_id", nullable = false)
    val tutorProfile: TutorProfile? = null,

    var slotStatus: SlotStatus = SlotStatus.AVAILABLE,

    var type: LessonType = LessonType.OPTIONAL,

    @Column(name="start_time")
    var startTime: Instant = Instant.now(),

    @Column(name="end_time")
    var endTime: Instant = Instant.now(),

    @OneToOne(mappedBy = "slot", cascade = [CascadeType.ALL], orphanRemoval = true)
    var lesson: Lesson? = null,
    ) {

    fun reserve() {
        if (slotStatus != SlotStatus.AVAILABLE) {
            throw SlotUnavailableException(
                String.format(
                    Constant.UNAVAILABLE_SLOT, id
                )
            )
        }

        slotStatus = SlotStatus.RESERVED
    }

    fun book() {
        if (slotStatus != SlotStatus.RESERVED) {
            throw SlotUnavailableException(
                String.format(
                    Constant.UNAVAILABLE_SLOT, id
                )
            )
        }
    }

    fun release() {
        if (slotStatus != SlotStatus.AVAILABLE) {
            throw InvalidSlotStatusException(
                String.format(
                    Constant.INVALID_SLOT_STATUS, slotStatus
                )
            )
        }
    }
}