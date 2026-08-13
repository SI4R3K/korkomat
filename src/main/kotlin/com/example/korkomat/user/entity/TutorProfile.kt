package com.example.korkomat.user.entity

import com.example.korkomat.lesson.entity.AvailableSlot
import com.example.korkomat.lesson.entity.TutorSubject
import com.example.korkomat.lesson.entity.enumeration.Lesson
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
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "tutor_profile")
data class TutorProfile(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(length = 1000)
    val bio: String,

    @Column(nullable = false)
    val hourlyRate: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_subject_id")
    val tutorSubject: TutorSubject? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    val user: User,

    @OneToMany(mappedBy = "tutor_profile", cascade = [CascadeType.ALL], orphanRemoval = true)
    val availableSlots: MutableList<AvailableSlot> = mutableListOf(),

    @OneToMany(mappedBy = "lesson", cascade = [CascadeType.ALL], orphanRemoval = true)
    val lessons: MutableList<Lesson> = mutableListOf(),

    )
