package reservation.project.domain.user.service.impl

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reservation.project.application.security.util.JwtUtils
import reservation.project.domain.user.entity.Role
import reservation.project.domain.user.entity.Customer
import reservation.project.domain.user.repository.UserRepository
import reservation.project.domain.user.service.UserService
import reservation.project.presentation.advice.exception.InvalidInfoException
import reservation.project.presentation.advice.exception.UserExistException
import reservation.project.presentation.response.ResponseDto
import reservation.project.presentation.user.dto.LoginReqDto
import reservation.project.presentation.user.dto.RegisterReqDto

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils,
    private val passwordEncoder: PasswordEncoder
): UserService {
    override fun findByUsername(username: String): ResponseDto {
        val value = userRepository.findByUsername(username).isPresent
        return ResponseDto(200, "Success", value)
    }

    override fun save(registerReqDto: RegisterReqDto): ResponseDto {
        if (userRepository.findByUsername(registerReqDto.username).isPresent) {
           throw UserExistException(ResponseDto(409, "Data already Exists", null));
        }

        val customer = Customer(username=registerReqDto.username,
            password = passwordEncoder.encode(registerReqDto.password), role = Role.USER)
        userRepository.save(customer).orElseThrow {
            throw Exception("User Save Error")
        }
        return ResponseDto(200, "Success", true)
    }

    override fun login(loginReqDto: LoginReqDto): ResponseDto {
        val user = userRepository.findByUsername(loginReqDto.username).orElseThrow {
            InvalidInfoException(ResponseDto(403, "InvalidInfo", null))
        }

        if(!passwordEncoder.matches(loginReqDto.password, user.password)
            ) {
            throw InvalidInfoException(ResponseDto(403, "Invalid Credential", null))
        }

        val jwtToken = jwtUtils.generateToken(loginReqDto.username)
        return ResponseDto(200, "Success", jwtToken)
    }
}