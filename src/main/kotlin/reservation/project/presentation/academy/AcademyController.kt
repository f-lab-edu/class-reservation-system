package reservation.project.presentation.academy

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.application.academy.AcademyUseCase
import reservation.project.presentation.academy.dto.AcademyReqDto
import reservation.project.presentation.response.ResponseDataDto

@RestController
@RequestMapping("/academy")
class AcademyController(
    private val academyUseCase: AcademyUseCase
) {

    @PostMapping("/register")
    fun registerAcademy(@RequestBody academyReqDto: AcademyReqDto): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDataDto(200, "Success", academyUseCase.registerAcademy(academyReqDto)))
    }

    @PostMapping("/searching")
    fun searchingAcademy(@PathVariable academyId: Int): ResponseEntity<*> {
        return ResponseEntity.ok(ResponseDataDto(200, "Success", academyUseCase.searchingAcademy(academyId)))
    }
}