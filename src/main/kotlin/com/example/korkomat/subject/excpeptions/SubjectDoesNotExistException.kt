package com.example.korkomat.subject.excpeptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class SubjectDoesNotExistException(message: String) : RuntimeException(message) {
}