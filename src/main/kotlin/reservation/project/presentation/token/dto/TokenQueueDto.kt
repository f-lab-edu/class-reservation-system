package reservation.project.presentation.token.dto

import jakarta.validation.constraints.NotEmpty

data class TokenQueueDto(
    @field:NotEmpty(message = "유저정보를 확인해주세요")
    val userId: Long,
    @field:NotEmpty(message = "수업정보를 확인해주세요")
    val classId: Long,
)
