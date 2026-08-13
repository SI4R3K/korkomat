package com.example.korkomat.lesson.entity.enumeration

import com.example.korkomat.auth.authorization.Role
import com.example.korkomat.lesson.entity.AvailableSlot
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "lesson")
class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "lesson_status")
    var status: LessonStatus = LessonStatus.PENDING

    var level: SubjectLevel? = null

    var place: String? = null

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "slot_id")
    var slot: AvailableSlot = AvailableSlot()

//    @Column(name="start_time")
//    var startTime: Instant = Instant.now()
//
//    @Column(name="end_time")
//    var endTime: Instant = Instant.now()
//
//    @Column(name="proposed_start_time")
//    var proposedStartTime: Instant = Instant.now()
//
//    @Column(name="proposed_end_time")
//    var proposedEndTime: Instant = Instant.now()
//
//    @Column(name="reschedule_request_by")
//    var rescheduleRequestBy: Role? = null
//
}