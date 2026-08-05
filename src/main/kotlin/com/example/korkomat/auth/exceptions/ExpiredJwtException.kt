package com.example.korkomat.auth.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ExpiredJwtException(message: String) : ResponseStatusException(HttpStatus.UNAUTHORIZED, message)
