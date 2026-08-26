package com.example.korkomat.auth.service

import com.example.korkomat.auth.dto.request.LoginRequest
import com.example.korkomat.auth.dto.request.LogoutRequest
import com.example.korkomat.auth.dto.request.RegisterRequest
import com.example.korkomat.auth.dto.request.TokenRefreshRequest
import com.example.korkomat.auth.dto.response.LoginResponse
import com.example.korkomat.auth.dto.response.LogoutResponse
import com.example.korkomat.auth.dto.response.RegistrationResponse
import com.example.korkomat.auth.dto.response.TokenRefreshResponse
import com.example.korkomat.auth.email.service.EmailService
import com.example.korkomat.auth.entity.ConfirmationToken
import com.example.korkomat.auth.exceptions.RefreshTokenNotFoundExcpetion
import com.example.korkomat.auth.exceptions.UnauthenticatedUserException
import com.example.korkomat.auth.exceptions.UserAlreadyExistsException
import com.example.korkomat.auth.exceptions.RefreshTokenExpiredException
import com.example.korkomat.auth.repository.RefreshTokenRepository
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.user.UserUtil
import com.example.korkomat.user.entity.User
import com.example.korkomat.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class AuthenticationServiceImpl(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val refreshTokenService: RefreshTokenService,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val confirmationTokenService: ConfirmationTokenService,
    private val emailService: EmailService,
): AuthenticationService {

    @Transactional
    override fun registerUser(
        request: RegisterRequest
    ): RegistrationResponse {

        val existingUser = userRepository.findByEmail(request.email)

        if (existingUser != null) {
            if (existingUser.isActive) {
                throw UserAlreadyExistsException(
                    String.format(
                        Constant.USER_ALREADY_EXISTS,
                        request.email
                    )
                )
            }
            val confirmationToken =
                confirmationTokenService
                    .createOrRenewConfirmationToken(existingUser)

            emailService.sendVerificationEmail(
                existingUser.email,
                confirmationToken.token
            )

            return RegistrationResponse(
                "Verification e-mail sent again. Please check your inbox ${existingUser.email}."
            )
        }

        requireNotNull(request.password) {
            "Password must not be null"
        }

        val user = User(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            password = User.encryptPassword(request.password)
        )

        val savedUser = userRepository.save(user)

        val confirmationToken =
            confirmationTokenService
                .createOrRenewConfirmationToken(savedUser)

         emailService.sendVerificationEmail(
             savedUser.email,
             confirmationToken.token
         )

        return RegistrationResponse(
            "Verification e-mail sent. Please check your inbox ${savedUser.email}."
        )
    }

    override fun confirmUser(confirmationToken: String) {
        confirmationTokenService.validateConfirmationToken(confirmationToken)
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
