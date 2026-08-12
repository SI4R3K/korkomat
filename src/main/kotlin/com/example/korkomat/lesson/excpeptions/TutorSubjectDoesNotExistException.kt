package com.example.korkomat.lesson.excpeptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class TutorSubjectDoesNotExistException(message: String) : RuntimeException(message)
