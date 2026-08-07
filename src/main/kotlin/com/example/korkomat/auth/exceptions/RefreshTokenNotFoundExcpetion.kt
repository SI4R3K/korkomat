package com.example.korkomat.auth.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class RefreshTokenNotFoundExcpetion(message: String) : ResponseStatusException(HttpStatus.NOT_FOUND, message) {
}