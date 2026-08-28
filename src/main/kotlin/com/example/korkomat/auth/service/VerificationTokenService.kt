package com.example.korkomat.auth.service

import com.example.korkomat.auth.entity.VerificationToken
import com.example.korkomat.auth.entity.enumeration.VerificationTokenType
import com.example.korkomat.user.entity.User

interface VerificationTokenService {
    fun saveVerificationToken(verificationToken: VerificationToken): VerificationToken
    fun getVerificationByToken(verificationToken: String, type: VerificationTokenType): VerificationToken
    fun getVerificationByUser(user: User, type: VerificationTokenType): VerificationToken
    fun createOrRenewVerificationToken(user: User, type: VerificationTokenType): VerificationToken
    fun validateVerificationToken(verificationToken: String, type: VerificationTokenType): VerificationToken
}