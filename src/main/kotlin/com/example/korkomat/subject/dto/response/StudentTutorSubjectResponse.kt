package com.example.korkomat.subject.dto.response

import com.example.korkomat.subject.entity.enumeration.SubjectLevel

data class StudentTutorSubjectsResponse(
    val tutorSubjects: List<StudentTutorSubjectResponse>
)

data class StudentTutorSubjectResponse(
    val subjectId: Long?,
    val subjectName: String,
    val level: SubjectLevel,
    val description: String?,
)
