package reservation.project.presentation.token.dto

import jakarta.validation.constraints.NotNull

data class TokenQueueDto(
    @field:NotNull(message = "유저정보를 확인해주세요")
    val userId: Long,
    @field:NotNull(message = "수업정보를 확인해주세요")
    val classId: Long,
)
