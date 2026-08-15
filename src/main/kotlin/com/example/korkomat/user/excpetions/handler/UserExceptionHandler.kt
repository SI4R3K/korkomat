package com.example.korkomat.user.excpetions.handler

import com.example.korkomat.common.constant.Constant
import com.example.korkomat.common.constant.ErrorStatus
import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.user.excpetions.InvalidProfileException
import com.example.korkomat.user.excpetions.UserNotFoundException
import com.example.korkomat.user.excpetions.UserProfileAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@RestController
class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(e: Exception): ResponseEntity<Api<Any>> {
        val errorResponse = Api.error<Any>(
            message = e.message ?: Constant.USER_NOT_FOUND,
            errorStatus = ErrorStatus.NOT_FOUND
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(UserProfileAlreadyExistsException::class)
    fun handleUserProfileAlreadyExistsException(e: Exception): ResponseEntity<Api<Any>> {
        val errorResponse = Api.error<Any>(
            message = e.message ?: Constant.PROFILE_ALREADY_EXISTS,
            errorStatus = ErrorStatus.NOT_FOUND
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFoundException(e: UsernameNotFoundException): ResponseEntity<Api<Any>> {
        val errorResponse = Api.error<Any>(
            message = e.message ?: Constant.USER_NOT_FOUND,
            errorStatus = ErrorStatus.NOT_FOUND
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(InvalidProfileException::class)
    fun handleInvalidProfileException(e: Exception): ResponseEntity<Api<Any>> {
        val errorResponse = Api.error<Any>(
            message = e.message ?: Constant.INVALID_PROFILE_TYPE,
            errorStatus = ErrorStatus.BAD_REQUEST
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}