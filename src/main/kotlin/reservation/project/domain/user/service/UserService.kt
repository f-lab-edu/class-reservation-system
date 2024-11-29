package reservation.project.domain.user.service

import reservation.project.domain.user.entity.Customer
import reservation.project.presentation.response.ResponseDto
import reservation.project.presentation.user.dto.LoginReqDto
import reservation.project.presentation.user.dto.RegisterReqDto

interface UserService {

    fun findByUsername(username: String): ResponseDto
    fun save(registerReqDto: RegisterReqDto):ResponseDto
    fun login(loginReqDto: LoginReqDto): ResponseDto
}