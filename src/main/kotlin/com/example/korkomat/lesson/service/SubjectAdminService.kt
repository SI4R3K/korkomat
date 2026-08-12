package com.example.korkomat.lesson.service

import com.example.korkomat.lesson.dto.request.CreateSubjectRequest
import com.example.korkomat.lesson.dto.request.UpdateSubjectRequest
import com.example.korkomat.lesson.dto.response.CreateSubjectResponse
import com.example.korkomat.lesson.dto.response.DeleteSubjectResponse
import com.example.korkomat.lesson.dto.response.GetSubjectResponse
import com.example.korkomat.lesson.dto.response.GetSubjectsResponse
import com.example.korkomat.lesson.dto.response.UpdateSubjectResponse

interface SubjectAdminService {
    fun createSubject(request: CreateSubjectRequest): CreateSubjectResponse
    fun getSubjects(): GetSubjectsResponse
    fun getSubject(id: String): GetSubjectResponse
    fun updateSubject(id: String, request: UpdateSubjectRequest): UpdateSubjectResponse
    fun deleteSubject(id: String): DeleteSubjectResponse
}