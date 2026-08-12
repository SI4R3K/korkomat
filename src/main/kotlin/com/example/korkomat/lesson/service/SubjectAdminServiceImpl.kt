package com.example.korkomat.lesson.service

import com.example.korkomat.common.constant.Constant
import com.example.korkomat.lesson.dto.request.CreateSubjectRequest
import com.example.korkomat.lesson.dto.request.UpdateSubjectRequest
import com.example.korkomat.lesson.dto.response.CreateSubjectResponse
import com.example.korkomat.lesson.dto.response.DeleteSubjectResponse
import com.example.korkomat.lesson.dto.response.GetSubjectResponse
import com.example.korkomat.lesson.dto.response.GetSubjectsResponse
import com.example.korkomat.lesson.dto.response.SubjectResponse
import com.example.korkomat.lesson.dto.response.UpdateSubjectResponse
import com.example.korkomat.lesson.entity.Subject
import com.example.korkomat.lesson.excpeptions.SubjectAlreadyExistsException
import com.example.korkomat.lesson.excpeptions.SubjectDoesNotExistException
import com.example.korkomat.lesson.repository.SubjectRepository
import com.example.korkomat.user.dto.response.AdminGetUsersResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubjectAdminServiceImpl(
    private val subjectRepository: SubjectRepository
) : SubjectAdminService {

    override fun createSubject(request: CreateSubjectRequest): CreateSubjectResponse {

        if (request.name.isEmpty()) {
            throw NullPointerException("Name is required")
        }

        if (subjectRepository.existsByName(request.name)) {
            throw SubjectAlreadyExistsException("Such subject already exists")
        }

        subjectRepository.save(
            Subject(name = request.name,)
        )

        return CreateSubjectResponse(
            message = "Created Successfully!",
        )
    }

    @Transactional(readOnly = true)
    override fun getSubjects(): GetSubjectsResponse {
        return GetSubjectsResponse(
            subjectRepository.findAll().map { it.toSubjectResponse() }
        )
    }

    @Transactional(readOnly = true)
    override fun getSubject(id: String): GetSubjectResponse {
        val subject = subjectRepository.findById(id.toLong())
            .orElseThrow {
                SubjectDoesNotExistException(String.format(Constant.SUBJECT_NOT_FOUND, id))
            }
        return GetSubjectResponse(
            subject = subject.toSubjectResponse(),
        )
    }

    @Transactional
    override fun updateSubject(
        id: String,
        request: UpdateSubjectRequest
    ): UpdateSubjectResponse {
        if (request.newName.isBlank()) {
            throw NullPointerException("Name is required")
        }

        val subject = subjectRepository.findById(id.toLong())
            .orElseThrow {
                SubjectDoesNotExistException(String.format(Constant.SUBJECT_NOT_FOUND, id))
            }

        if (subjectRepository.existsByName(request.newName)) {
            throw SubjectAlreadyExistsException(
                String.format(Constant.USER_ALREADY_EXISTS, request.newName))
        }

        subject.name = request.newName
//        with Transactional annotation there is no need for subjectRepository.save(subject) and
//        changes in entity will be saved with transaction commit

        return UpdateSubjectResponse(
            message = "Updated Successfully!",
        )
    }

    override fun deleteSubject(id: String): DeleteSubjectResponse {
        val subject = subjectRepository.findById(id.toLong())
            .orElseThrow {
                SubjectDoesNotExistException(String.format(Constant.SUBJECT_NOT_FOUND, id))
            }
        subjectRepository.delete(subject)
        return DeleteSubjectResponse(
            message = "Deleted!",
        )
    }

    private fun Subject.toSubjectResponse(): SubjectResponse {
        return SubjectResponse(
            id = id,
            name = name,
        )
    }
}