package com.example.korkomat.lesson.entity

import com.example.korkomat.slot.entity.AvailableSlot
import com.example.korkomat.subject.entity.TutorSubject
import com.example.korkomat.lesson.entity.enumeration.LessonStatus
import com.example.korkomat.lesson.exceptions.InvalidLessonStatusException
import com.example.korkomat.user.entity.StudentProfile
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "lessons")
data class Lesson(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_status")
    var status: LessonStatus = LessonStatus.PENDING,

    @OneToOne
    @JoinColumn(name = "available_slot_id")
    var slot: AvailableSlot,

    @ManyToOne
    @JoinColumn(name = "student_profile_id")
    var studentProfile: StudentProfile,

    @ManyToOne
    @JoinColumn(name = "tutor_subject_id")
    var tutorSubject: TutorSubject? = null,

    var place: String? = null,
){
    fun confirm() {
        if (status != LessonStatus.PENDING) {
            throw InvalidLessonStatusException(
                "Lesson status has already been updated. Current status is $status."
            )
        }

        status = LessonStatus.CONFIRMED
    }

    fun reject() {
        if (status == LessonStatus.REJECTED) {
            throw InvalidLessonStatusException(
                "Lesson has already been rejected."
            )
        }
        status = LessonStatus.REJECTED
    }



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