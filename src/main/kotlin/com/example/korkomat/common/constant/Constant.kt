package com.example.korkomat.common.constant

object Constant {
    const val USER_ALREADY_EXISTS = "A user with this [%s] already exists."
    const val USER_NOT_FOUND = "A user with this [%s] does not exist."

    // Authentication Errors
    const val AUTHENTICATION_FAILED = "User authentication failed."

    // JWT Token Errors
    const val JWT_MALFORMED = "The JWT token is malformed."
    const val JWT_EXPIRED = "The JWT token has expired."
    const val JWT_UNSUPPORTED = "The JWT token type is unsupported."

}