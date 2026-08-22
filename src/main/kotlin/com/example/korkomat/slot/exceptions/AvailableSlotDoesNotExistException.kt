package com.example.korkomat.slot.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class AvailableSlotDoesNotExistException(message: String) : RuntimeException(message) {
}