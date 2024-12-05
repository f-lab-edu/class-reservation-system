package reservation.project.presentation.academy

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.presentation.academy.dto.AcademyReqDto

@RestController
@RequestMapping("/academy")
class AcademyController {

    @PostMapping("/register")
    fun registerAcademy(@RequestBody academyReqDto: AcademyReqDto): ResponseEntity<*> {
        return ResponseEntity.ok("회원가입 성공")
    }
}