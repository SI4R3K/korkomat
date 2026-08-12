package com.example.korkomat.lesson.service

import com.example.korkomat.lesson.dto.request.CreateTutorSubjectRequest
import com.example.korkomat.lesson.dto.request.UpdateTutorSubjectRequest
import com.example.korkomat.lesson.dto.response.CreateTutorSubjectResponse
import com.example.korkomat.lesson.dto.response.DeleteTutorSubjectResponse
import com.example.korkomat.lesson.dto.response.TutorSubjectDetailsResponse
import com.example.korkomat.lesson.dto.response.TutorSubjectsResponse
import com.example.korkomat.lesson.dto.response.UpdateTutorSubjectResponse

interface TutorSubjectService {
    fun createTutorSubject(request: CreateTutorSubjectRequest): CreateTutorSubjectResponse
    fun getMyTutorSubjects(): TutorSubjectsResponse
    fun getMyTutorSubject(id: Long): TutorSubjectDetailsResponse
    fun updateTutorSubject(id: Long, request: UpdateTutorSubjectRequest): UpdateTutorSubjectResponse
    fun deleteTutorSubject(id: Long): DeleteTutorSubjectResponse
}
