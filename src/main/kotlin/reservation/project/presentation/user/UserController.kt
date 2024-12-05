package reservation.project.presentation.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.domain.user.service.UserService
import reservation.project.presentation.response.ResponseDto
import reservation.project.presentation.user.dto.LoginReqDto
import reservation.project.presentation.user.dto.RegisterReqDto

@RestController
@RequestMapping("/auth")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(@RequestBody registerReqDto: RegisterReqDto): ResponseEntity<*> {
        try {
            return ResponseEntity.ok(ResponseDto(200, "회원가입 성공", userService.save(registerReqDto)))
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }
    @PostMapping("/login")
    fun login(@RequestBody loginReqDto: LoginReqDto): ResponseEntity<*> {
        try {
            return ResponseEntity.ok(ResponseDto(200, "회원가입 성공", userService.login(loginReqDto)))
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }
}