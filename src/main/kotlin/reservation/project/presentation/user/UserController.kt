package reservation.project.presentation.user

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.application.customer.AuthUseCase
import reservation.project.presentation.response.ResponseDataDto
import reservation.project.presentation.response.ResponseDto
import reservation.project.presentation.user.dto.LoginReqDto
import reservation.project.presentation.user.dto.RegisterReqDto

@RestController
@RequestMapping("/auth")
class UserController(
    private val authUseCase: AuthUseCase
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody registerReqDto: RegisterReqDto): ResponseDto<Boolean> {
        return authUseCase.signUp(registerReqDto)
    }
    @PostMapping("/login")
    fun login(@Valid @RequestBody loginReqDto: LoginReqDto): ResponseDataDto<String> {
        return authUseCase.signIn(loginReqDto)
    }
}