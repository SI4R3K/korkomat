package com.example.korkomat.lesson.dto.response

import com.example.korkomat.lesson.entity.enumeration.SubjectLevel

data class StudentTutorSubjectsResponse(
    val tutorSubjects: List<StudentTutorSubjectResponse>
)

data class StudentTutorSubjectResponse(
    val subjectId: Long?,
    val subjectName: String,
    val level: SubjectLevel,
    val description: String?,
)
