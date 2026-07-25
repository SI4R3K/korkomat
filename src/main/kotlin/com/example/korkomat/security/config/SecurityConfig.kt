package com.example.korkomat.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            // 1. Wyłączamy ochronę CSRF (dla bezstanowego REST API)
            csrf { disable() }

            // 2. Zezwalamy na KAŻDE żądanie HTTP bez autoryzacji
            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }

            // 3. Wyłączamy domyślny formularz logowania oraz HTTP Basic
            formLogin { disable() }
            httpBasic { disable() }
        }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}