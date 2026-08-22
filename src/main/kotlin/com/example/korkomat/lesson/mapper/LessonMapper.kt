package com.example.korkomat.lesson.mapper

import com.example.korkomat.lesson.dto.response.StudentLessonResponse
import com.example.korkomat.lesson.dto.response.TutorLessonResponse
import com.example.korkomat.lesson.entity.Lesson

fun Lesson.toStudentLessonResponse(): StudentLessonResponse {
    return StudentLessonResponse(
        id = id,
        status = status,
        startTime = slot.startTime,
        endTime = slot.endTime,
        place = place,
        subjectName = tutorSubject?.subject?.name,
        tutorName = tutorSubject?.tutor?.user?.getFullName()
    )
}

fun Lesson.toTutorLessonResponse(): TutorLessonResponse {
    return TutorLessonResponse(
        id = id,
        status = status,
        startTime = slot.startTime,
        endTime = slot.endTime,
        place = place,
        subjectName = tutorSubject?.subject?.name,
        level = tutorSubject?.level,
        studentName = studentProfile.user.getFullName(),
    )
}