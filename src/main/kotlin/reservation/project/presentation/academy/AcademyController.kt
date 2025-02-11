package reservation.project.presentation.academy

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import reservation.project.application.academy.AcademyUseCase
import reservation.project.presentation.academy.dto.academy.AcademyRegisterInfoDto
import reservation.project.presentation.academy.dto.academy.AcademyUpdateInfoReqDto
import reservation.project.presentation.academy.dto.academy.InstructorRegisterInfoDto
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

    @Tag(name = "Academy Instructor Regist", description = "학원 강사 등록")
    @PatchMapping("/instructor/register")
    fun registerAcademyInstructor(@Valid @RequestBody request: InstructorRegisterInfoDto): ResponseDto<Boolean> {
        return academyUseCase.registerAcademyInstructorInfo(request)
    }
}
