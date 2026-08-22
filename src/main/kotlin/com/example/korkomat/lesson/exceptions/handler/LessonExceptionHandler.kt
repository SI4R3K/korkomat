package com.example.korkomat.lesson.exceptions.handler

import com.example.korkomat.common.constant.Constant
import com.example.korkomat.common.constant.ErrorStatus
import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.lesson.exceptions.NoLessonsException
import com.example.korkomat.common.dto.response.Api.Companion.error
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class LessonExceptionHandler {

    @ExceptionHandler(NoLessonsException::class)
    fun handleNoLessonsException(e: NoLessonsException): ResponseEntity<Api<Any>> {
        val errorResponse = error<Any>(
            message = e.message ?: Constant.NO_LESSONS,
            errorStatus = ErrorStatus.NOT_FOUND,
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }
}