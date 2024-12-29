package reservation.project.presentation.academy.dto

import jakarta.validation.constraints.NotBlank

data class AcademyClassReqDto(
    @field:NotBlank(message = "유효하지 않은 학원정보입니다.")
    val academyId: Long,

    @field:NotBlank(message = "유효하지 않은 강사정보입니다.")
    val adminId: Long
)
