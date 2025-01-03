package reservation.project.presentation.admin

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.presentation.admin.dto.AdminRegisterReqDto
import reservation.project.presentation.response.ResponseDto

@RestController
@RequestMapping("/admin")
class AdminController {

    @PostMapping("/register")
    fun register(@RequestBody adminRegisterReqDto: AdminRegisterReqDto): ResponseDto<String> {
            return ResponseDto(200, "Success")
    }
}