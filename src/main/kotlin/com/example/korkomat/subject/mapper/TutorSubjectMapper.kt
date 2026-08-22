package com.example.korkomat.subject.mapper

import com.example.korkomat.subject.dto.response.StudentTutorSubjectResponse
import com.example.korkomat.subject.dto.response.TutorSubjectResponse
import com.example.korkomat.subject.entity.TutorSubject

fun TutorSubject.toTutorSubjectResponse(): TutorSubjectResponse {
    return TutorSubjectResponse(
        id = id,
        tutorId = tutor.id,
        tutorEmail = tutor.user.email,
        tutorFullName = tutor.user.getFullName(),
        subjectId = subject.id,
        subjectName = subject.name,
        level = level,
        description = description,
    )
}

fun TutorSubject.toStudentTutorSubjectResponse(): StudentTutorSubjectResponse {
    return StudentTutorSubjectResponse(
        subjectId = id,
        subjectName = subject.name,
        level = level,
        description = description
    )
}
