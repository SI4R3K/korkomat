package com.example.korkomat.auth.service

import com.example.korkomat.auth.dto.request.LoginRequest
import com.example.korkomat.auth.dto.request.RegisterRequest
import com.example.korkomat.auth.dto.response.LoginResponse
import com.example.korkomat.auth.exceptions.UnauthenticatedUserException
import com.example.korkomat.auth.exceptions.UserAlreadyExistsException
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.user.domain.User
import com.example.korkomat.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager
): AuthenticationService {

    @Transactional
    override fun registerUser(request: RegisterRequest): String {
        if (userRepository.existsByEmail(request.email)) {
            throw UserAlreadyExistsException(
                String.format(Constant.USER_ALREADY_EXISTS, request.email)
            )
        }

        requireNotNull(request.password) { "Password must not be null" }
        val passwordHash = User.encryptPassword(request.password)

        val user = User(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            password = passwordHash,
        )
        userRepository.save(user)
        return "Zarejestrowano użytkownika o ID: ${user.id}"
    }

    @Transactional
    override fun loginUser(request: LoginRequest): LoginResponse {
        if (
            !userRepository.existsByEmail(
                requireNotNull(request.email) {"Email must not be null"})
        ) {
            throw UsernameNotFoundException(String.format(Constant.USER_NOT_FOUND, request.email))
        }

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )

        if (!authentication.isAuthenticated) {
            throw UnauthenticatedUserException(Constant.AUTHENTICATION_FAILED)
        }

//
//        val user = userRepository.findPasswordByEmail(request.email)
//        val authentications = request.password == user?.getPass()
//
//        if(!authentication) {
//            throw UserAlreadyExistsException(Constant.AUTHENTICATION_FAILED)
//        }

        return LoginResponse("Udalo sie ;)")
    }
}