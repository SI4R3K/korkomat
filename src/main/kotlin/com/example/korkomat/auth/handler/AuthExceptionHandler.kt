package com.example.korkomat.auth.handler

import com.example.korkomat.auth.exceptions.ForbiddenAccessException
import com.example.korkomat.auth.exceptions.RefreshTokenExpiredException
import com.example.korkomat.auth.exceptions.UserAlreadyExistsException
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.common.constant.ErrorStatus
import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.common.dto.response.Api.Companion.error
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
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

    @ExceptionHandler(RefreshTokenExpiredException::class)
    fun handleRefreshTokenExpired(
        ex: RefreshTokenExpiredException
    ): ResponseEntity<Api<Nothing>> {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                Api.error(
                    ex.message ?: "Refresh token expired",
                    ErrorStatus.UNAUTHORIZED
                )
            )
    }

    @ExceptionHandler(ForbiddenAccessException::class)
    fun handleAccessDeniedException(e: ForbiddenAccessException): ResponseEntity<Api<Any>> {
        val errorResponse = Api.error<Any>(
            message = e.message ?: Constant.AUTH_ACCESS_DENIED,
            errorStatus = ErrorStatus.FORBIDDEN
        )
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(e: BadCredentialsException): ResponseEntity<Api<Any>> {
        val errorResponse = Api.error<Any>(
            message = e.message ?: Constant.ERROR_BAD_REQUEST,
            errorStatus = ErrorStatus.BAD_REQUEST
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleExpiredJwtException(e: ExpiredJwtException): ResponseEntity<Api<Any>> {
        val errorResponse = Api.error<Any>(
            message = e.message ?: Constant.JWT_EXPIRED,
            errorStatus = ErrorStatus.UNAUTHORIZED
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }

    @ExceptionHandler(MalformedJwtException::class)
    fun handleMalformedJwtException(e: MalformedJwtException): ResponseEntity<Api<Any>> {
        val errorResponse = Api.error<Any>(
            message = e.message ?: Constant.JWT_MALFORMED,
            errorStatus = ErrorStatus.UNAUTHORIZED
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }

    @ExceptionHandler(UnsupportedJwtException::class)
    fun handleUnsupportedJwtException(e: UnsupportedJwtException): ResponseEntity<Api<Any>> {
        val errorResponse = Api.error<Any>(
            message = e.message ?: Constant.JWT_UNSUPPORTED,
            errorStatus = ErrorStatus.UNAUTHORIZED
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<Api<Any>> {
        val errorResponse = Api.error<Any>(
            message = e.message ?: Constant.ERROR_INTERNAL_SERVER,
            errorStatus = ErrorStatus.INTERNAL_SERVER_ERROR
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }

}