package reservation.project.presentation.token.dto

import jakarta.validation.constraints.NotEmpty
import org.jetbrains.annotations.NotNull

data class TokenQueueSetDto(
    @field:NotEmpty(message = "토큰값을 확인해주세요")
    val jwt: String
)
