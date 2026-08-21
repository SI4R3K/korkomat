package com.example.korkomat.lesson.service

import com.example.korkomat.auth.exceptions.UnauthenticatedUserException
import com.example.korkomat.auth.service.CurrentProfileService
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.lesson.dto.request.CreateTutorSubjectRequest
import com.example.korkomat.lesson.dto.request.UpdateTutorSubjectRequest
import com.example.korkomat.lesson.dto.response.CreateTutorSubjectResponse
import com.example.korkomat.lesson.dto.response.DeleteTutorSubjectResponse
import com.example.korkomat.lesson.dto.response.TutorSubjectDetailsResponse
import com.example.korkomat.lesson.dto.response.TutorSubjectsResponse
import com.example.korkomat.lesson.dto.response.UpdateTutorSubjectResponse
import com.example.korkomat.lesson.entity.TutorSubject
import com.example.korkomat.lesson.excpeptions.SubjectAlreadyExistsException
import com.example.korkomat.lesson.excpeptions.SubjectDoesNotExistException
import com.example.korkomat.lesson.excpeptions.TutorSubjectDoesNotExistException
import com.example.korkomat.lesson.mapper.toTutorSubjectResponse
import com.example.korkomat.lesson.repository.SubjectRepository
import com.example.korkomat.lesson.repository.TutorSubjectRepository
import com.example.korkomat.user.entity.TutorProfile
import com.example.korkomat.user.excpetions.UserNotFoundException
import com.example.korkomat.user.repository.TutorRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TutorSubjectServiceImpl(
    private val tutorSubjectRepository: TutorSubjectRepository,
    private val subjectRepository: SubjectRepository,
    private val currentProfileService: CurrentProfileService
) : TutorSubjectService {

    @Transactional
    override fun createTutorSubject(request: CreateTutorSubjectRequest): CreateTutorSubjectResponse {
        val tutor = currentProfileService.getCurrentTutor()
        val subject = subjectRepository.findById(request.subjectId)
            .orElseThrow {
                SubjectDoesNotExistException(String.format(Constant.SUBJECT_NOT_FOUND, request.subjectId))
            }

        if (tutorSubjectRepository.existsByTutorIdAndSubjectIdAndLevel(
                requireNotNull(tutor.id),
                request.subjectId,
                request.level,
            )
        ) {
            throw SubjectAlreadyExistsException("Tutor subject already exists")
        }

        val tutorSubject = tutorSubjectRepository.save(
            TutorSubject(
                tutor = tutor,
                subject = subject,
                level = request.level,
                description = request.description,
            )
        )

        return CreateTutorSubjectResponse(
            message = "Tutor subject created successfully!",
            tutorSubject = tutorSubject.toTutorSubjectResponse(),
        )
    }

    @Transactional(readOnly = true)
    override fun getMyTutorSubjects(): TutorSubjectsResponse {
        val tutor = currentProfileService.getCurrentTutor()
        return TutorSubjectsResponse(
            tutorSubjects = tutorSubjectRepository.findAllByTutorId(requireNotNull(tutor.id))
                .map { it.toTutorSubjectResponse() }
        )
    }

    @Transactional(readOnly = true)
    override fun getMyTutorSubject(id: Long): TutorSubjectDetailsResponse {
        return TutorSubjectDetailsResponse(
            tutorSubject = getCurrentTutorSubject(id).toTutorSubjectResponse()
        )
    }

    @Transactional
    override fun updateTutorSubject(
        id: Long,
        request: UpdateTutorSubjectRequest
    ): UpdateTutorSubjectResponse {
        val tutor = currentProfileService.getCurrentTutor()
        val tutorSubject = findTutorSubjectForTutor(id, tutor)

        // verifying requested data
        val targetSubject = request.subjectId?.let { subjectId ->
            subjectRepository.findById(subjectId)
                .orElseThrow {
                    SubjectDoesNotExistException(String.format(Constant.SUBJECT_NOT_FOUND, subjectId))
                }
        } ?: tutorSubject.subject
        // check if the new subjectId was in the request if true get new Subject from DB else leave current one

        val targetLevel = request.level ?: tutorSubject.level

        if (tutorSubjectRepository.existsByTutorIdAndSubjectIdAndLevelAndIdNot(
                requireNotNull(tutor.id),
                requireNotNull(targetSubject.id),
                targetLevel,
                id,
            )
        ) {
            throw SubjectAlreadyExistsException("Tutor subject already exists")
        }

        // updating existing entity
        request.subjectId?.let {
            tutorSubject.subject = targetSubject
        } // if subjectId==null leave it else update the tutorSubject
        request.level?.let {
            tutorSubject.level = targetLevel
        }
        request.description?.let {
            tutorSubject.description = it
        }

        return UpdateTutorSubjectResponse(
            message = "Tutor subject updated successfully!",
            tutorSubject = tutorSubject.toTutorSubjectResponse(),
        )
    }

    @Transactional
    override fun deleteTutorSubject(id: Long): DeleteTutorSubjectResponse {
        val tutorSubject = getCurrentTutorSubject(id)
        tutorSubjectRepository.delete(tutorSubject)
        return DeleteTutorSubjectResponse(
            message = "Tutor subject deleted successfully!"
        )
    }

    private fun getCurrentTutorSubject(id: Long): TutorSubject {
        val tutor = currentProfileService.getCurrentTutor()
        return findTutorSubjectForTutor(id, tutor)
    }

    private fun findTutorSubjectForTutor(id: Long, tutor: TutorProfile): TutorSubject {
        return tutorSubjectRepository.findByIdAndTutorId(id, requireNotNull(tutor.id))
            ?: throw TutorSubjectDoesNotExistException("Tutor subject with this [$id] does not exist.")
    }
}
