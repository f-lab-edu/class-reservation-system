package reservation.project.presentation.academy.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AcademyClassReqDto(
    @field:NotNull(message = "유효하지 않은 학원정보입니다.")
    val academyId: Long,

    @field:NotNull(message = "유효하지 않은 강사정보입니다.")
    val adminId: Long
)
