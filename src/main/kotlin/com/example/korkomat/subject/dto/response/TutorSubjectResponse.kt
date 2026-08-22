package com.example.korkomat.subject.dto.response

import com.example.korkomat.subject.entity.enumeration.SubjectLevel
import java.util.UUID

data class TutorSubjectsResponse(
    val tutorSubjects: List<TutorSubjectResponse>,
)

data class TutorSubjectDetailsResponse(
    val tutorSubject: TutorSubjectResponse,
)

data class CreateTutorSubjectResponse(
    val message: String,
    val tutorSubject: TutorSubjectResponse,
)

data class UpdateTutorSubjectResponse(
    val message: String,
    val tutorSubject: TutorSubjectResponse,
)

data class DeleteTutorSubjectResponse(
    val message: String,
)

data class TutorSubjectResponse(
    val id: Long?,
    val tutorId: UUID?,
    val tutorEmail: String,
    val tutorFullName: String,
    val subjectId: Long?,
    val subjectName: String,
    val level: SubjectLevel,
    val description: String?,
)
