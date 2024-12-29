package reservation.project.presentation.admin

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.presentation.admin.dto.AdminRegisterReqDto
import reservation.project.presentation.response.ResponseDataDto

@RestController
@RequestMapping("/admin")
class AdminController {

    @PostMapping("/register")
    fun register(@RequestBody adminRegisterReqDto: AdminRegisterReqDto): ResponseEntity<*> {
        try {
            return ResponseEntity.ok(ResponseDataDto(200, "회원가입 성공", null))
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }
}