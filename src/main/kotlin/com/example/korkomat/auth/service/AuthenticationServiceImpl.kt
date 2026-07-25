package com.example.korkomat.auth.service

import com.example.korkomat.auth.dto.request.RegisterRequest
import com.example.korkomat.user.domain.User
import com.example.korkomat.user.repository.UserRepository

class AuthenticationServiceImpl(
    private val userRepository: UserRepository
): AuthenticationService {
    override fun registerUser(request: RegisterRequest): String {
        if (userRepository.existsByEmail(request.email)) {
            throw RuntimeException("Email zajety!")
        }
        val user = User(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            password = request.password
        )
        userRepository.save(user)
        return "Zarejestrowano użytkownika o ID: ${user.id}"
    }
}