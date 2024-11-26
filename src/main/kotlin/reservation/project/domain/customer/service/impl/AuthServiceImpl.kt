package reservation.project.domain.customer.service.impl

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reservation.project.application.security.util.JwtUtils
import reservation.project.domain.customer.entity.Role
import reservation.project.domain.customer.entity.User
import reservation.project.domain.customer.repository.UserRepository
import reservation.project.domain.customer.service.AuthService
import reservation.project.presentation.user.dto.LoginReqDto
import reservation.project.presentation.user.dto.RegisterReqDto

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils,
    private val passwordEncoder: PasswordEncoder
): AuthService {
    override fun findByUsername(username: String): User {
        return userRepository.findByUsername(username)
            .orElse(null)
    }

    override fun save(registerReqDto: RegisterReqDto): User {
        if (userRepository.findByUsername(registerReqDto.username).isPresent) {
           throw IllegalArgumentException("User already exists")
        }
        val uname = registerReqDto.username
        val pw = passwordEncoder.encode(registerReqDto.password)

        val user = User(username=uname, password = pw, role = Role.USER)

        return userRepository.save(user)
    }

    override fun login(loginReqDto: LoginReqDto): String {
        val user = userRepository.findByUsername(loginReqDto.username).orElse(null)
        if(user != null && !passwordEncoder.matches(loginReqDto.password, user.password) ) {
            throw IllegalArgumentException("Invalid credentials")
        }

        val jwtToken = jwtUtils.generateToken(loginReqDto.username)
        return jwtToken
    }
}