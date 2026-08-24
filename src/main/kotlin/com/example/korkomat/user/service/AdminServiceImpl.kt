package com.example.korkomat.user.service

import com.example.korkomat.common.constant.Constant
import com.example.korkomat.subject.entity.TutorSubject
import com.example.korkomat.user.dto.response.AdminGetStudentResponse
import com.example.korkomat.user.dto.response.AdminGetStudentsResponse
import com.example.korkomat.user.dto.response.AdminGetTutorResponse
import com.example.korkomat.user.dto.response.AdminGetTutorsResponse
import com.example.korkomat.user.dto.response.AdminGetUserResponse
import com.example.korkomat.user.dto.response.AdminGetUsersResponse
import com.example.korkomat.user.dto.response.AdminStudentResponse
import com.example.korkomat.user.dto.response.AdminTutorResponse
import com.example.korkomat.user.dto.response.AdminTutorSubjectResponse
import com.example.korkomat.user.dto.response.AdminUserResponse
import com.example.korkomat.user.entity.StudentProfile
import com.example.korkomat.user.entity.TutorProfile
import com.example.korkomat.user.entity.User
import com.example.korkomat.user.excpetions.UserNotFoundException
import com.example.korkomat.user.repository.StudentRepository
import com.example.korkomat.user.repository.TutorRepository
import com.example.korkomat.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class AdminServiceImpl(
    private val userRepository: UserRepository,
    private val studentRepository: StudentRepository,
    private val tutorRepository: TutorRepository
) : AdminService {

    @Transactional(readOnly = true)
    override fun getUsers(): AdminGetUsersResponse {
        return AdminGetUsersResponse(
            users = userRepository.findAll().map { it.toAdminResponse() }
        )
    }

    @Transactional(readOnly = true)
    override fun getUser(id: String): AdminGetUserResponse {
        val user = userRepository.findById(id.toUuid())
            .orElseThrow { UserNotFoundException(String.format(Constant.USER_NOT_FOUND, id)) }

        return AdminGetUserResponse(user = user.toAdminResponse())
    }

    @Transactional(readOnly = true)
    override fun getStudents(): AdminGetStudentsResponse {
        return AdminGetStudentsResponse(
            students = studentRepository.findAll().map { it.toAdminResponse() }
        )
    }

    @Transactional(readOnly = true)
    override fun getStudent(id: String): AdminGetStudentResponse {
        val student = studentRepository.findById(id.toUuid())
            .orElseThrow { UserNotFoundException("Student profile with id [$id] does not exist.") }

        return AdminGetStudentResponse(student = student.toAdminResponse())
    }

    @Transactional(readOnly = true)
    override fun getTutors(): AdminGetTutorsResponse {
        return AdminGetTutorsResponse(
            tutors = tutorRepository.findAll().map { it.toAdminResponse() }
        )
    }

    @Transactional(readOnly = true)
    override fun getTutor(id: String): AdminGetTutorResponse {
        val tutor = tutorRepository.findById(id.toUuid())
            .orElseThrow { UserNotFoundException("Tutor profile with id [$id] does not exist.") }

        return AdminGetTutorResponse(tutor = tutor.toAdminResponse())
    }

    private fun String.toUuid(): UUID {
        return try {
            UUID.fromString(this)
        } catch (ex: IllegalArgumentException) {
            throw UserNotFoundException("Resource with id [$this] does not exist.")
        }
    }

    private fun User.toAdminResponse(): AdminUserResponse {
        return AdminUserResponse(
            id = id,
            email = email,
            firstName = firstName,
            lastName = lastName,
            role = role,
            isActive = isActive,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private fun StudentProfile.toAdminResponse(): AdminStudentResponse {
        return AdminStudentResponse(
            id = id,
            user = user.toAdminResponse()
        )
    }

    private fun TutorProfile.toAdminResponse(): AdminTutorResponse {
        return AdminTutorResponse(
            id = id,
            bio = bio,
            hourlyRate = hourlyRate,
            tutorSubjects = tutorSubjects.map { it.toAdminResponse() },
            user = user.toAdminResponse()
        )
    }

    private fun TutorSubject.toAdminResponse(): AdminTutorSubjectResponse {
        return AdminTutorSubjectResponse(
            id = id,
            subject = subject.name,
            level = level,
            description = description
        )
    }
}
