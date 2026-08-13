package com.example.korkomat.lesson.entity

import com.example.korkomat.lesson.entity.enumeration.Lesson
import com.example.korkomat.lesson.entity.enumeration.LessonType
import com.example.korkomat.lesson.entity.enumeration.SlotStatus
import com.example.korkomat.user.entity.TutorProfile
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "available_slots")
class AvailableSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "tutor_profile_id", nullable = false)
    val tutorProfile: TutorProfile? = null

    var slotStatus: SlotStatus = SlotStatus.AVAILABLE

    var type: LessonType = LessonType.OPTIONAL

    @Column(name="start_time")
    var startTime: Instant = Instant.now()

    @Column(name="end_time")
    var endTime: Instant = Instant.now()

    @OneToOne(mappedBy = "availableSlot", cascade = [CascadeType.ALL])
    var lesson: Lesson? = null
}