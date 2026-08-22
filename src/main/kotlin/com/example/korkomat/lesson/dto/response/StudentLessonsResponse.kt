package com.example.korkomat.lesson.dto.response

import com.example.korkomat.lesson.entity.enumeration.LessonStatus
import com.example.korkomat.subject.entity.enumeration.SubjectLevel
import java.time.Instant

data class GetLessonsResponse(
    val lessons: List<LessonResponse>
)

sealed interface LessonResponse {
    val id: Long?
    val status: LessonStatus
    val startTime: Instant
    val endTime: Instant
    val place: String?
    val subjectName: String?
}

data class StudentLessonResponse(
    override val id: Long?,
    override val status: LessonStatus,
    override val startTime: Instant,
    override val endTime: Instant,
    override val place: String?,
    override val subjectName: String?,
    val tutorName: String?
): LessonResponse

data class TutorLessonResponse(
    override val id: Long?,
    override val status: LessonStatus,
    override val startTime: Instant,
    override val endTime: Instant,
    override val place: String?,
    override val subjectName: String?,
    val level: SubjectLevel?,
    val studentName: String,
): LessonResponse

