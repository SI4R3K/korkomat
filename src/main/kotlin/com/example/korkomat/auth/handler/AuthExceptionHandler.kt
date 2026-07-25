package com.example.korkomat.auth.handler

import com.example.korkomat.auth.exceptions.UserAlreadyExistsException
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.common.constant.ErrorStatus
import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.common.dto.response.Api.Companion.error
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuthExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(e: UserAlreadyExistsException): ResponseEntity<Api<Any>> {
        val errorResponse = error<Any>(
            message = e.message ?: Constant.USER_ALREADY_EXISTS,
            errorStatus = ErrorStatus.CONFLICT
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse)
    }
}