package com.example.korkomat.lesson.entity

import com.example.korkomat.lesson.entity.enumeration.LessonStatus
import com.example.korkomat.lesson.entity.enumeration.SubjectLevel
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "lesson")
class Lesson {

    @Id
    @Column(name = "available_slot_id")
    var id: Long? = null

    @Column(name = "lesson_status")
    var status: LessonStatus = LessonStatus.PENDING

    var level: SubjectLevel? = null

    var place: String? = null

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "available_slot_id")
    lateinit var slot: AvailableSlot

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
