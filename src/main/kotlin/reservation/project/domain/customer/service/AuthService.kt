package reservation.project.domain.customer.service

import reservation.project.domain.customer.entity.User
import reservation.project.presentation.user.dto.LoginReqDto
import reservation.project.presentation.user.dto.RegisterReqDto

interface AuthService {

    fun findByUsername(username: String):User
    fun save(registerReqDto: RegisterReqDto):User
    fun login(loginReqDto: LoginReqDto): String
}