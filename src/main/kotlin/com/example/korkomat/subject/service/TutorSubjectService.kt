package com.example.korkomat.subject.service

import com.example.korkomat.subject.dto.request.CreateTutorSubjectRequest
import com.example.korkomat.subject.dto.request.UpdateTutorSubjectRequest
import com.example.korkomat.subject.dto.response.CreateTutorSubjectResponse
import com.example.korkomat.subject.dto.response.DeleteTutorSubjectResponse
import com.example.korkomat.subject.dto.response.StudentTutorSubjectsResponse
import com.example.korkomat.subject.dto.response.TutorSubjectDetailsResponse
import com.example.korkomat.subject.dto.response.TutorSubjectsResponse
import com.example.korkomat.subject.dto.response.UpdateTutorSubjectResponse
import java.util.UUID

interface TutorSubjectService {
    fun createTutorSubject(request: CreateTutorSubjectRequest): CreateTutorSubjectResponse
    fun getMyTutorSubjects(): TutorSubjectsResponse
    fun getMyTutorSubject(id: Long): TutorSubjectDetailsResponse
    fun getTutorSubjects(tutorId: UUID): StudentTutorSubjectsResponse
    fun updateTutorSubject(id: Long, request: UpdateTutorSubjectRequest): UpdateTutorSubjectResponse
    fun deleteTutorSubject(id: Long): DeleteTutorSubjectResponse
}
