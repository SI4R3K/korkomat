package com.example.korkomat.lesson.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NoLessonsException(message: String): RuntimeException(message) {
}