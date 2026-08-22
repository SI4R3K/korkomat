package com.example.korkomat.lesson.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT)
class InvalidLessonStatusException(message: String): RuntimeException(message) {
}
