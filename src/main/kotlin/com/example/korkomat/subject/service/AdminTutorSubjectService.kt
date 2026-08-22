package com.example.korkomat.subject.service

import com.example.korkomat.subject.dto.response.TutorSubjectDetailsResponse
import com.example.korkomat.subject.dto.response.TutorSubjectsResponse
import java.util.UUID

interface AdminTutorSubjectService {
    fun getTutorSubject(id: Long): TutorSubjectDetailsResponse
    fun searchTutorSubjects(
        id: Long?,
        subjectName: String?,
        tutorId: UUID?,
        tutorEmail: String?,
    ): TutorSubjectsResponse
}
