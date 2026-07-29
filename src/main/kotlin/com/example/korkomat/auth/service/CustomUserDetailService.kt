package com.example.korkomat.auth.service

import com.example.korkomat.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(mail: String): UserDetails {
        val user = userRepository.findByEmail(mail!!)
            ?: throw UsernameNotFoundException("User not found with email: $mail")

        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            user.authorities
        )
    }
}