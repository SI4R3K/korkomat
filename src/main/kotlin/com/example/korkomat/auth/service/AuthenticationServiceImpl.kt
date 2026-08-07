package com.example.korkomat.auth.service

import com.example.korkomat.auth.dto.request.LoginRequest
import com.example.korkomat.auth.dto.request.LogoutRequest
import com.example.korkomat.auth.dto.request.RegisterRequest
import com.example.korkomat.auth.dto.request.TokenRefreshRequest
import com.example.korkomat.auth.dto.response.LoginResponse
import com.example.korkomat.auth.dto.response.LogoutResponse
import com.example.korkomat.auth.dto.response.TokenRefreshResponse
import com.example.korkomat.auth.exceptions.RefreshTokenNotFoundExcpetion
import com.example.korkomat.auth.exceptions.UnauthenticatedUserException
import com.example.korkomat.auth.exceptions.UserAlreadyExistsException
import com.example.korkomat.auth.exceptions.RefreshTokenExpiredException
import com.example.korkomat.auth.repository.RefreshTokenRepository
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.user.UserUtil
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
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val refreshTokenService: RefreshTokenService,
    private val refreshTokenRepository: RefreshTokenRepository,
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

        val principal = authentication.principal as org.springframework.security.core.userdetails.User
        val user = userRepository.findByEmail(principal.username)
            ?: throw UsernameNotFoundException(String.format(Constant.USER_NOT_FOUND, principal.username))

        val claims = mapOf("roles" to user.role)
        val accessToken = jwtService.generateToken(claims, user)
        val expiresIn = jwtService.expiresIn
        val tokenType = jwtService.tokenType
        val rawToken = refreshTokenService.generateRawRefreshToken()

        refreshTokenService.saveRefreshToken(rawToken, user)
        return UserUtil.tokensToLoginResponse(expiresIn, accessToken, tokenType, rawToken)
    }

    @Transactional(dontRollbackOn = [RefreshTokenExpiredException::class])
    override fun refreshAccessToken(request: TokenRefreshRequest): TokenRefreshResponse {
        val refreshToken = refreshTokenService.findByToken(request.refreshToken)

        refreshTokenService.verifyExpiration(refreshToken)

        val user = refreshToken.user
        val claims = mapOf("roles" to user.role)
        val accessToken = jwtService.generateToken(claims, user)
        val expiresIn = jwtService.expiresIn
        val tokenType = jwtService.tokenType

        return UserUtil.tokensToTokenRefreshResponse(
            accessToken,
            expiresIn,
            tokenType
        )
    }

    @Transactional
    override fun logout(request: LogoutRequest): LogoutResponse {

        if (request.refreshToken.isNullOrBlank()) {
            throw RefreshTokenNotFoundExcpetion(Constant.REFRESH_TOKEN_NOT_PROVIDED)
        }

        refreshTokenRepository.delete(
            refreshTokenService.findByToken(request.refreshToken)
        )

        return LogoutResponse("Log out successful")
    }
}