package reservation.project.application.auth

import reservation.project.presentation.admin.dto.AdminLoginReqDto
import reservation.project.presentation.admin.dto.AdminRegisterReqDto
import reservation.project.presentation.response.ResponseDto
import reservation.project.presentation.user.dto.LoginReqDto
import reservation.project.presentation.user.dto.RegisterReqDto

interface AuthUseCase {
    fun adminRegister(adminRegisterReqDto: AdminRegisterReqDto): ResponseDto
    fun adminLogin(adminLoginReqDto: AdminLoginReqDto): ResponseDto

    fun userRegister(registerReqDto: RegisterReqDto): ResponseDto
    fun userLogin(loginReqDto: LoginReqDto): ResponseDto
}