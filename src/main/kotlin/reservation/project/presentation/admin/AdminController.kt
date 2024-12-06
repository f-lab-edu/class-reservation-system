package reservation.project.presentation.admin

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.application.auth.AuthUseCase
import reservation.project.presentation.admin.dto.AdminLoginReqDto
import reservation.project.presentation.admin.dto.AdminRegisterReqDto
import reservation.project.presentation.response.ResponseDto

@RestController
@RequestMapping("/admin")
class AdminController (
    private val authUseCase: AuthUseCase
){

    @PostMapping("/register")
    fun register(@RequestBody adminRegisterReqDto: AdminRegisterReqDto): ResponseEntity<*> {
        try {
            return ResponseEntity.ok(ResponseDto(200, "회원가입 성공", authUseCase.adminRegister(adminRegisterReqDto)))
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody adminLoginReqDto: AdminLoginReqDto): ResponseEntity<*> {
        try {
            return ResponseEntity.ok(ResponseDto(200, "회원가입 성공", authUseCase.adminLogin(adminLoginReqDto)))
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }
}