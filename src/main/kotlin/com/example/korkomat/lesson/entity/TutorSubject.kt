package com.example.korkomat.lesson.entity

import com.example.korkomat.user.entity.TutorProfile
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "tutor_subjects",
    uniqueConstraints = [
        UniqueConstraint(
            columnNames = ["tutor_id","subject_id", "level"]
        )
    ]
)
data class TutorSubject(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tutor_id", nullable = false)
    val tutor: TutorProfile,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    val subject: Subject,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val level: SubjectLevel,

    @Column(length = 1000)
    val description: String? = null,

//    TODO()
//    @OneToMany(mappedBy = "tutorSubject")
//    val lessons: MutableList<Lesson> = mutableListOf()
)
