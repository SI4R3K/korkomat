package com.example.korkomat.lesson.excpeptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class InvalidSlotTimeException(msg: String) : RuntimeException(msg) {
}