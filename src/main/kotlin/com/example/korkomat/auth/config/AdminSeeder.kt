package com.example.korkomat.auth.config

import com.example.korkomat.auth.authorization.Role
import com.example.korkomat.user.entity.User
import com.example.korkomat.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class AdminSeeder(
    private val userRepository: UserRepository,
    @Value("\${app.admin.seed.enabled:false}")
    private val enabled: Boolean,
    @Value("\${app.admin.seed.email:admin@korkomat.pl}")
    private val email: String,
    @Value("\${app.admin.seed.password:admin123}")
    private val password: String,
    @Value("\${app.admin.seed.first-name:Admin}")
    private val firstName: String,
    @Value("\${app.admin.seed.last-name:Korkomat}")
    private val lastName: String
) : CommandLineRunner {

    override fun run(vararg args: String) {
        if (!enabled) {
            return
        }

        val existingUser = userRepository.findByEmail(email)

        if (existingUser != null) {
            existingUser.role = Role.ADMIN
            existingUser.isActive = true
            userRepository.save(existingUser)
            return
        }

        userRepository.save(
            User(
                email = email,
                password = User.encryptPassword(password),
                firstName = firstName,
                lastName = lastName,
                role = Role.ADMIN,
                isActive = true
            )
        )
    }
}
