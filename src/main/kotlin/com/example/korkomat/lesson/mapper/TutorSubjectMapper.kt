package com.example.korkomat.lesson.mapper

import com.example.korkomat.lesson.dto.response.TutorSubjectResponse
import com.example.korkomat.lesson.entity.TutorSubject

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
