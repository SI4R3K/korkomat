package com.example.korkomat.lesson.repository

import com.example.korkomat.lesson.entity.Lesson
import com.example.korkomat.user.entity.StudentProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.Instant

interface LessonRepository: JpaRepository<Lesson, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END
        FROM Lesson l
        WHERE l.studentProfile = :studentProfile
        AND l.slot.startTime < :endTime
        AND l.slot.endTime > :startTime
    """)
    fun existsOverlappingLesson(
        studentProfile: StudentProfile,
        startTime: Instant,
        endTime: Instant
    ): Boolean
}