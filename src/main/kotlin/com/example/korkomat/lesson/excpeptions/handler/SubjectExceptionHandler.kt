package com.example.korkomat.lesson.excpeptions.handler

import com.example.korkomat.common.constant.Constant
import com.example.korkomat.common.constant.ErrorStatus
import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.common.dto.response.Api.Companion.error
import com.example.korkomat.lesson.excpeptions.SubjectAlreadyExistsException
import com.example.korkomat.lesson.excpeptions.SubjectDoesNotExistException
import com.example.korkomat.lesson.excpeptions.TutorSubjectDoesNotExistException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class SubjectExceptionHandler {

    @ExceptionHandler(SubjectAlreadyExistsException::class)
    fun handleSubjectAlreadyExistsException(e: SubjectAlreadyExistsException): ResponseEntity<Api<Any>> {
        val errorResponse = error<Any>(
            message = e.message ?: Constant.SUBJECT_ALREADY_EXISTS,
            errorStatus = ErrorStatus.CONFLICT
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse)
    }

    @ExceptionHandler(SubjectDoesNotExistException::class)
    fun handleSubjectDoesNotExistException(e: SubjectDoesNotExistException): ResponseEntity<Api<Any>> {
        val errorResponse = error<Any>(
            message = e.message ?: Constant.SUBJECT_NOT_FOUND,
            errorStatus = ErrorStatus.NOT_FOUND
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(TutorSubjectDoesNotExistException::class)
    fun handleTutorSubjectDoesNotExistException(e: TutorSubjectDoesNotExistException): ResponseEntity<Api<Any>> {
        val errorResponse = error<Any>(
            message = e.message ?: Constant.ENTITY_NOT_FOUND,
            errorStatus = ErrorStatus.NOT_FOUND
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

}
