package com.example.korkomat.auth.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT)
class UserAlreadyExistsException(message : String?): RuntimeException(message)