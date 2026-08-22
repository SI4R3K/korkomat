package com.example.korkomat.slot.exceptions.handler

import com.example.korkomat.common.constant.Constant
import com.example.korkomat.common.constant.ErrorStatus
import com.example.korkomat.common.dto.response.Api
import com.example.korkomat.common.dto.response.Api.Companion.error
import com.example.korkomat.slot.exceptions.InvalidSlotStatusException
import com.example.korkomat.slot.exceptions.InvalidSlotTimeException
import com.example.korkomat.slot.exceptions.SlotUnavailableException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class SlotExceptionHandler {

    @ExceptionHandler(InvalidSlotTimeException::class)
    fun handleInvalidSlotTimeException(e: InvalidSlotTimeException): ResponseEntity<Api<Any>> {
        val errorResponse = error<Any>(
            message = e.message ?: Constant.INVALID_PROPOSED_TIME,
            errorStatus = ErrorStatus.CONFLICT
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse)
    }

    @ExceptionHandler(SlotUnavailableException::class)
    fun handleSlotUnavailableException(e: SlotUnavailableException): ResponseEntity<Api<Any>> {
        val errorResponse = error<Any>(
            message = e.message ?: Constant.UNAVAILABLE_SLOT,
            errorStatus = ErrorStatus.CONFLICT
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse)
    }
    @ExceptionHandler(InvalidSlotStatusException::class)
    fun handleInvalidSlotStatusException(e: InvalidSlotStatusException): ResponseEntity<Api<Any>> {
        val errorResponse = error<Any>(
            message = e.message ?: Constant.INVALID_SLOT_STATUS,
            errorStatus = ErrorStatus.CONFLICT
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse)
    }

}