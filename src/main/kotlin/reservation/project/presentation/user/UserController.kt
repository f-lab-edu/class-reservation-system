package reservation.project.presentation.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.presentation.dto.reqeust.LoginReqDto
import reservation.project.presentation.dto.reqeust.RegisterReqDto

@RestController
@RequestMapping("/auth")
class UserController(
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

            return ResponseEntity.ok("로그인 성공")
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }
}