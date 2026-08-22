package com.example.korkomat.subject.service

import com.example.korkomat.subject.dto.request.CreateSubjectRequest
import com.example.korkomat.subject.dto.request.UpdateSubjectRequest
import com.example.korkomat.subject.dto.response.CreateSubjectResponse
import com.example.korkomat.subject.dto.response.DeleteSubjectResponse
import com.example.korkomat.subject.dto.response.GetSubjectResponse
import com.example.korkomat.subject.dto.response.GetSubjectsResponse
import com.example.korkomat.subject.dto.response.UpdateSubjectResponse

interface SubjectAdminService {
    fun createSubject(request: CreateSubjectRequest): CreateSubjectResponse
    fun getSubjects(): GetSubjectsResponse
    fun getSubject(id: String): GetSubjectResponse
    fun updateSubject(id: String, request: UpdateSubjectRequest): UpdateSubjectResponse
    fun deleteSubject(id: String): DeleteSubjectResponse
}