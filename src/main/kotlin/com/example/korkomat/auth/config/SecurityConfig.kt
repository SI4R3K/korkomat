package com.example.korkomat.auth.config

import com.example.korkomat.auth.filter.JwtAuthenticationFilter
import com.example.korkomat.auth.service.CustomUserDetailService
import com.example.korkomat.user.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // pozwala na @PreAuthorize
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {
    companion object {
        private val WHITE_LIST = arrayOf(
            "/error",
            "/auth/login",
            "/auth/logout",
            "/auth/register",
            "/auth/forgot-password",
            "/auth/reset-password",
            "/auth/validate-password/**",
            "/auth/refreshtoken",
        )
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { cors -> cors.configurationSource(corsConfigurationSource()) }
            .csrf { request -> request.disable() }
            .httpBasic { }
            .authorizeHttpRequests { request ->
                request
                    .requestMatchers("/admin/**").hasAuthority("ADMIN")
                    .requestMatchers(*WHITE_LIST).permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { request -> request.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun userDetailsService(
        userRepository: UserRepository,
    ): UserDetailsService {
        return CustomUserDetailService(userRepository)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(
        passwordEncoder: PasswordEncoder,
        customUserDetailService: CustomUserDetailService
    ): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider(customUserDetailService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)
        return authenticationProvider
    }

    @Bean
    fun authenticationManager(
        authenticationProvider: AuthenticationProvider
    ): AuthenticationManager {
        return ProviderManager(authenticationProvider)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
