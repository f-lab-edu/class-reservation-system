package reservation.project.application.auth

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reservation.project.application.security.util.JwtUtils
import reservation.project.domain.admin.entity.Admin
import reservation.project.domain.admin.service.AdminService
import reservation.project.domain.user.entity.Customer
import reservation.project.domain.user.entity.Role
import reservation.project.domain.user.service.UserService
import reservation.project.presentation.admin.dto.AdminLoginReqDto
import reservation.project.presentation.admin.dto.AdminRegisterReqDto
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.presentation.response.ResponseDataDto
import reservation.project.presentation.response.ResponseDto
import reservation.project.presentation.user.dto.LoginReqDto
import reservation.project.presentation.user.dto.RegisterReqDto
import java.math.BigDecimal

@Component
class AuthUseCase(
    private val userService: UserService,
    private val adminService: AdminService,
    private val jwtUtils: JwtUtils,
    private val passwordEncoder: PasswordEncoder
) {
     fun adminRegister(adminRegisterReqDto: AdminRegisterReqDto):ResponseDataDto<String> {
        TODO("Not yet implemented")
    }

     fun adminLogin(adminLoginReqDto: AdminLoginReqDto):ResponseDto {
        if (adminService.findByAdminName(adminLoginReqDto.username).isPresent) {
        throw ErrorException(ResponseDto(409, "Data already Exists"));
         }

        val admin = Admin(adminName = adminLoginReqDto.username,
            password = passwordEncoder.encode(adminLoginReqDto.password), role = Role.ADMIN, academy = null)
        adminService.save(admin).orElseThrow {
            throw ErrorException(ResponseDto(500,"Admin Save Error"))
        }
        return ResponseDto(200, "Success")
    }


     fun userRegister(registerReqDto: RegisterReqDto): ResponseDto {
        if (userService.findByUsername(registerReqDto.username).isPresent) {
            throw ErrorException(ResponseDto(409, "Data already Exists"));
        }

        val customer = Customer(0, BigDecimal.ZERO,username=registerReqDto.username,
            password = passwordEncoder.encode(registerReqDto.password), role = Role.USER)
        userService.save(customer).orElseThrow {
            throw ErrorException(ResponseDto(500, "User Save Error"))
        }
        return ResponseDto(200, "Success")
    }

     fun userLogin(loginReqDto: LoginReqDto): ResponseDataDto<String> {
        val user = userService.findByUsername(loginReqDto.username).orElseThrow {
            ErrorException(ResponseDto(403, "Invalid Info"))
        }

        if(!passwordEncoder.matches(loginReqDto.password, user.password)
        ) {
            throw ErrorException(ResponseDto(403, "Invalid Info"))
        }

        val jwtToken = jwtUtils.generateToken(loginReqDto.username)
        return ResponseDataDto(200, "Success", jwtToken)
    }
}