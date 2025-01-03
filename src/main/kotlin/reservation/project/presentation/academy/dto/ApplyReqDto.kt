package reservation.project.presentation.academy.dto

import jakarta.validation.constraints.NotNull

data class ApplyReqDto(

    @field:NotNull(message = "유효하지 않은 수업정보입니다.")
    val classId: Long,

    @field:NotNull(message = "유효하지 않은 유저정보입니다.")
    val userId: Long,
)
