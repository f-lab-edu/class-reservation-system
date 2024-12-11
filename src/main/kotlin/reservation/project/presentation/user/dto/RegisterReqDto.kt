package reservation.project.presentation.user.dto

import org.jetbrains.annotations.NotNull
import jakarta.

data class RegisterReqDto(

    @field:NotNull
    val username: String,
    val password: String
)
