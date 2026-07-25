package com.example.korkomat.auth.service

import com.example.korkomat.auth.dto.request.RegisterRequest
import com.example.korkomat.auth.exceptions.UserAlreadyExistsException
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.user.domain.User
import com.example.korkomat.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
    private val userRepository: UserRepository
): AuthenticationService {

    @Transactional
    override fun registerUser(request: RegisterRequest): String {
        if (userRepository.existsByEmail(request.email)) {
            throw UserAlreadyExistsException(
                String.format(Constant.USER_ALREADY_EXISTS, request.email)
            )
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