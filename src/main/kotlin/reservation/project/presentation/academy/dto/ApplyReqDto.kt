package reservation.project.presentation.academy.dto

import jakarta.validation.constraints.NotBlank

data class ApplyReqDto(

    @field:NotBlank(message = "유효하지 않은 수업정보입니다.")
    val classId: Long,

    @field:NotBlank(message = "유효하지 않은 유저정보입니다.")
    val userId: Long,
)
