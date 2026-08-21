package com.example.korkomat.lesson.excpeptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class SlotUnavailableException(message: String) : RuntimeException(message) {
}