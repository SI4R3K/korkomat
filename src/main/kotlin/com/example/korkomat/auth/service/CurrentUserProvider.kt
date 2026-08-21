package com.example.korkomat.auth.service

import com.example.korkomat.auth.exceptions.UnauthenticatedUserException
import com.example.korkomat.user.entity.User
import com.example.korkomat.user.excpetions.UserNotFoundException
import com.example.korkomat.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class CurrentUserProvider(
    private val userRepository: UserRepository,
) {
    fun getCurrentUser(): User {
        val email = SecurityContextHolder.getContext()
        .authentication
        ?.name
            ?: throw UnauthenticatedUserException(
                "Authenticated user not found in security context."
            )

        return userRepository.findByEmail(email)
            ?: throw UserNotFoundException(
                "User not found"
            )
    }

    fun getCurrentUserEmail(): String {
        return SecurityContextHolder.getContext()
            .authentication
            ?.name
                ?: throw UnauthenticatedUserException(
                    "Authenticated user not found in security context."
                )
    }
}