package reservation.project.application.auth.impl

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reservation.project.application.auth.AuthUseCase
import reservation.project.application.security.util.JwtUtils
import reservation.project.domain.academy.service.SecretManagementService
import reservation.project.domain.admin.entity.Admin
import reservation.project.domain.admin.service.AdminService
import reservation.project.domain.user.entity.Customer
import reservation.project.domain.user.entity.Role
import reservation.project.domain.user.service.UserService
import reservation.project.presentation.admin.dto.AdminLoginReqDto
import reservation.project.presentation.admin.dto.AdminRegisterReqDto
import reservation.project.presentation.advice.exception.InvalidInfoException
import reservation.project.presentation.advice.exception.NoInfoException
import reservation.project.presentation.advice.exception.UserExistException
import reservation.project.presentation.response.ResponseDto
import reservation.project.presentation.user.dto.LoginReqDto
import reservation.project.presentation.user.dto.RegisterReqDto

@Component
class AuthUseCaseImpl(
    private val userService: UserService,
    private val adminService: AdminService,
    private val secretManagementService: SecretManagementService,
    private val jwtUtils: JwtUtils,
    private val passwordEncoder: PasswordEncoder
): AuthUseCase {
    override fun adminRegister(adminRegisterReqDto: AdminRegisterReqDto): ResponseDto {

        if (adminService.findByAdminName(adminRegisterReqDto.username).isPresent) {
            throw UserExistException(ResponseDto(409, "Data already Exists", null));
        }

        // secretKey를 이용하여 SecretManagement Entity 찾는 로직 추가
        val secretManagement = secretManagementService.findBySecretKey(adminRegisterReqDto.secretCode).orElseThrow {
            throw NoInfoException(ResponseDto(500, "Not Found SecretKey", null))
        }

        val admin = Admin(adminName = adminRegisterReqDto.username,
            password = passwordEncoder.encode(adminRegisterReqDto.password), role = Role.ADMIN, academy = secretManagement.academy)
        adminService.save(admin).orElseThrow {
            throw Exception("Admin Save Error")
        }

        return ResponseDto(200, "Success", true)
    }

    override fun adminLogin(adminLoginReqDto: AdminLoginReqDto): ResponseDto {
        val admin = adminService.findByAdminName(adminLoginReqDto.username).orElse(null)
            ?: throw InvalidInfoException(ResponseDto(403, "InvalidInfo Admin", null))

        if (!passwordEncoder.matches(adminLoginReqDto.password, admin.password)) {
            throw InvalidInfoException(ResponseDto(403, "Invalid Credential", null))
        }

        val jwtToken = jwtUtils.generateToken(adminLoginReqDto.username)
        return ResponseDto(200, "Success", jwtToken)
    }


    override fun userRegister(registerReqDto: RegisterReqDto): ResponseDto {
        if (userService.findByUsername(registerReqDto.username).isPresent) {
            throw UserExistException(ResponseDto(409, "Data already Exists", null));
        }

        val customer = Customer(username=registerReqDto.username,
            password = passwordEncoder.encode(registerReqDto.password), role = Role.USER)
        userService.save(customer).orElseThrow {
            throw Exception("User Save Error")
        }
        return ResponseDto(200, "Success", true)
    }

    override fun userLogin(loginReqDto: LoginReqDto): ResponseDto {
        val user = userService.findByUsername(loginReqDto.username).orElse(null)
            ?: throw InvalidInfoException(ResponseDto(403, "InvalidInfo User", null))

        if(!passwordEncoder.matches(loginReqDto.password, user.password)
        ) {
            throw InvalidInfoException(ResponseDto(403, "Invalid Credential", null))
        }

        val jwtToken = jwtUtils.generateToken(loginReqDto.username)
        return ResponseDto(200, "Success", jwtToken)
    }
}