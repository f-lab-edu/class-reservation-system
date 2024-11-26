package reservation.project.presentation.user

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.application.security.util.JwtUtils
import reservation.project.domain.customer.service.AuthService
import reservation.project.presentation.user.dto.LoginReqDto
import reservation.project.presentation.user.dto.RegisterReqDto

@RestController
@RequestMapping("/auth")
class UserController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(@RequestBody registerReqDto: RegisterReqDto): ResponseEntity<*> {
        try {

            return ResponseEntity.ok("회원가입 성공")
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }
    @PostMapping("/login")
    fun login(@RequestBody loginReqDto: LoginReqDto): ResponseEntity<*> {
        try {
            val jwtToken = authService.login(loginReqDto)
            return ResponseEntity.ok(jwtToken)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }
}