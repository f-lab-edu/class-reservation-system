package reservation.project.presentation.user

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.application.auth.AuthUseCase
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
    fun register(@Valid @RequestBody registerReqDto: RegisterReqDto): ResponseEntity<ResponseDto<String>> {
        return ResponseEntity.ok(authUseCase.userRegister(registerReqDto))
    }
    @PostMapping("/login")
    fun login(@Valid @RequestBody loginReqDto: LoginReqDto): ResponseEntity<ResponseDataDto<String>> {
        return ResponseEntity.ok(authUseCase.userLogin(loginReqDto))
    }
}