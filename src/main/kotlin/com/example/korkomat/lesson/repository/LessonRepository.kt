package com.example.korkomat.lesson.repository

import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.lesson.entity.enumeration.LessonStatus
import com.example.korkomat.user.entity.StudentProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant
import java.util.UUID

interface LessonRepository: JpaRepository<Lesson, Long> {

    fun findAllByStatusAndStudentProfileId(
        @Param("status") status: LessonStatus,
        @Param("studentProfileId") studentProfileId: UUID?
    ): List<Lesson>

    @Query("""
        SELECT l
        FROM Lesson l
        WHERE l.status = :status
        AND l.slot.tutorProfile.id = :tutorProfileId
    """)
    fun findAllByStatusAndTutorProfileId(
        @Param("status") status: LessonStatus,
        @Param("tutorProfileId") tutorProfileId: UUID?
    ): List<Lesson>

    @Query("""
    SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END
    FROM Lesson l
    WHERE l.studentProfile = :studentProfile
      AND l.status IN :activeStatuses
      AND l.slot.startTime < :endTime
      AND l.slot.endTime > :startTime
""")
    fun existsOverlappingLesson(
        studentProfile: StudentProfile,
        startTime: Instant,
        endTime: Instant,
        activeStatuses: List<LessonStatus>
    ): Boolean
}