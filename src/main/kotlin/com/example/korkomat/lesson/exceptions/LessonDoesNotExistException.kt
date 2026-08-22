package com.example.korkomat.lesson.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class LessonDoesNotExistException(message: String): RuntimeException(message) {
}