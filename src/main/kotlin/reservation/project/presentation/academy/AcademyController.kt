package reservation.project.presentation.academy

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reservation.project.application.academy.AcademyUseCase
import reservation.project.presentation.academy.dto.AcademyRegisterInfoDto
import reservation.project.presentation.academy.dto.AcademyUpdateInfoReqDto
import reservation.project.presentation.response.ResponseDataDto
import reservation.project.presentation.response.ResponseDto

@RestController
@RequestMapping("/academy")
class AcademyController(
    private val academyUseCase: AcademyUseCase
) {

    @Tag(name = "Academy Register", description = "학원 정보 등록 API")
    @PostMapping("/register")
    fun registerAcademy(@Valid @RequestBody request: AcademyRegisterInfoDto): ResponseDto<Boolean> {
        return academyUseCase.registerAcademyInfo(request)
    }

    @Tag(name = "Academy Update", description = "학원 정보 수정 API")
    @PatchMapping("/update")
    fun updateAcademy(@Valid @RequestBody request: AcademyUpdateInfoReqDto): ResponseDto<Boolean> {
        return academyUseCase.updateAcademyInfo(request)
    }
}