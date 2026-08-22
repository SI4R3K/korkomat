package com.example.korkomat.common.constant

import kotlin.time.Instant

object Constant {

    // User-Related Errors
    const val USER_ALREADY_EXISTS = "A user with this [%s] already exists."
    const val USER_NOT_FOUND = "A user with this [%s] does not exist."
    const val ILLEGAL_STATE = "The operation cannot be performed due to an invalid object state."

    const val STUDENT_PROFILE_ALREADY_EXISTS = "Student profile with this [%s] already exists."
    const val STUDENT_PROFILE_NOT_FOUND = "Student with this [%s] does not exist."
    const val TUTOR_PROFILE_ALREADY_EXISTS = "Tutor profile with this [%s] already exist."
    const val TUTOR_PROFILE_NOT_FOUND = "Tutor profile with this [%s] does not exist."

    const val PROFILE_ALREADY_EXISTS = "Profile with this [%s] already exist."
    const val INVALID_PROFILE_TYPE = "Invalid profile type"

    // Authentication Errors
    const val AUTHENTICATION_FAILED = "User authentication failed."
    const val AUTH_ACCESS_DENIED = "Access denied for this [%s]."

    // JWT Token Errors
    const val JWT_MALFORMED = "The JWT token is malformed."
    const val JWT_EXPIRED = "The JWT token has expired."
    const val JWT_UNSUPPORTED = "The JWT token type is unsupported."

    // Refresh Token Errors
    const val REFRESH_TOKEN_NOT_FOUND = "Refresh token not found."
    const val REFRESH_TOKEN_NOT_PROVIDED = "Refresh token was not provided."

    // Subject Errors
    const val SUBJECT_ALREADY_EXISTS = "Subject with this [%s] already exists."
    const val SUBJECT_NOT_FOUND = "Subject with this [%s] does not exist."

    // AvailableSlot Errors
    const val INVALID_PROPOSED_TIME = "Invalid proposed time."
    const val AVAILABLE_SLOT_NOT_FOUND = "Available slot with this [%s] was not found."
    const val UNAVAILABLE_SLOT = "Slot with this [%s] is unavailable."
    const val SLOT_RESERVED = "Slot with this [%s] is reserved."
    const val AVAILABLE_SLOT_ALREADY_STARTED = "Slot with this [%s] already started."
    const val INVALID_SLOT_STATUS = "Slot with status [%] is invalid."

    // Lessons Error
    const val LESSON_OVERLAPPING = "There exists an overlapping slot for this [%s]"
    const val NO_LESSONS = "There are no lessons to view"

    // General Errors
    const val ERROR_INTERNAL_SERVER = "An unexpected error occurred on the server."
    const val ERROR_BAD_REQUEST = "Invalid request or credentials."
    const val ENTITY_NOT_FOUND = "Entity not found"

}