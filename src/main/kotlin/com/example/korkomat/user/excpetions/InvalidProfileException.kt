package com.example.korkomat.user.excpetions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidProfileException(message: String) : RuntimeException(message) {
}