package com.example.korkomat.subject.excpeptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class SubjectAlreadyExistsException(msg: String) : RuntimeException(msg) {
}