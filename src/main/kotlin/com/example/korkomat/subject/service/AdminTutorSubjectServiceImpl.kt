package com.example.korkomat.subject.service

import com.example.korkomat.subject.dto.response.TutorSubjectDetailsResponse
import com.example.korkomat.subject.dto.response.TutorSubjectsResponse
import com.example.korkomat.subject.excpeptions.TutorSubjectDoesNotExistException
import com.example.korkomat.subject.mapper.toTutorSubjectResponse
import com.example.korkomat.subject.repository.TutorSubjectRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class AdminTutorSubjectServiceImpl(
    private val tutorSubjectRepository: TutorSubjectRepository,
) : AdminTutorSubjectService {

    @Transactional(readOnly = true)
    override fun getTutorSubject(id: Long): TutorSubjectDetailsResponse {
        val tutorSubject = tutorSubjectRepository.findById(id)
            .orElseThrow {
                TutorSubjectDoesNotExistException("Tutor subject with this [$id] does not exist.")
            }

        return TutorSubjectDetailsResponse(
            tutorSubject = tutorSubject.toTutorSubjectResponse()
        )
    }

    @Transactional(readOnly = true)
    override fun searchTutorSubjects(
        id: Long?,
        subjectName: String?,
        tutorId: UUID?,
        tutorEmail: String?,
    ): TutorSubjectsResponse {
        return TutorSubjectsResponse(
            tutorSubjects = tutorSubjectRepository.searchForAdmin(
                id = id,
                subjectNamePattern = subjectName?.toSearchPattern(),
                tutorId = tutorId,
                tutorEmailPattern = tutorEmail?.toSearchPattern(),
            ).map { it.toTutorSubjectResponse() }
        )
    }

    private fun String.toSearchPattern(): String? {
        return takeIf { it.isNotBlank() }
            ?.lowercase()
            ?.let { "%$it%" }
    }
}
