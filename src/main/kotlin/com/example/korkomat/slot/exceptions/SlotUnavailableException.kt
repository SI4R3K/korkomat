package com.example.korkomat.slot.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class SlotUnavailableException(message: String) : RuntimeException(message) {
}