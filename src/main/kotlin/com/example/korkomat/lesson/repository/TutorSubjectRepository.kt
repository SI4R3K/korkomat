package com.example.korkomat.lesson.repository

import com.example.korkomat.lesson.entity.enumeration.SubjectLevel
import com.example.korkomat.lesson.entity.TutorSubject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface TutorSubjectRepository : JpaRepository<TutorSubject, Long> {
    fun findAllByTutorId(tutorId: UUID): List<TutorSubject>

    fun findByIdAndTutorId(id: Long, tutorId: UUID): TutorSubject?

    fun existsByTutorIdAndSubjectIdAndLevel(tutorId: UUID, subjectId: Long, level: SubjectLevel): Boolean

    fun existsByTutorIdAndSubjectIdAndLevelAndIdNot(
        tutorId: UUID,
        subjectId: Long,
        level: SubjectLevel,
        id: Long,
    ): Boolean

    @Query(
        """
        select ts from TutorSubject ts
        join ts.subject s
        join ts.tutor t
        join t.user u
        where (:id is null or ts.id = :id)
          and (:subjectNamePattern is null or lower(s.name) like :subjectNamePattern)
          and (:tutorId is null or t.id = :tutorId)
          and (:tutorEmailPattern is null or lower(u.email) like :tutorEmailPattern)
        """
    )
    fun searchForAdmin(
        @Param("id") id: Long?,
        @Param("subjectNamePattern") subjectNamePattern: String?,
        @Param("tutorId") tutorId: UUID?,
        @Param("tutorEmailPattern") tutorEmailPattern: String?,
    ): List<TutorSubject>
}
