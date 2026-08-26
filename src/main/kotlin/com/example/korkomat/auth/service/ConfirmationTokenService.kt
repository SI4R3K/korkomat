package com.example.korkomat.auth.service

import com.example.korkomat.auth.entity.ConfirmationToken
import com.example.korkomat.user.entity.User

interface ConfirmationTokenService {
    fun saveConfirmationToken(confirmationToken: ConfirmationToken): ConfirmationToken
    fun getConfirmationByToken(token: String): ConfirmationToken
    fun getConfirmationByUser(user: User): ConfirmationToken
    fun createOrRenewConfirmationToken(user: User): ConfirmationToken
    fun validateConfirmationToken(confirmationToken: String)
}