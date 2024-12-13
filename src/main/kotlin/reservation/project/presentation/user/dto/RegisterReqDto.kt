package reservation.project.presentation.user.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class RegisterReqDto(

    @field:NotBlank(message = "사용하실 이름을 입력해주세요")
    val username: String,
    @field:Size(min = 10, message = "비밀번호는 최소 10자 이상이어야 합니다.")
    val password: String
)
