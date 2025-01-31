package reservation.project.presentation.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginReqDto(
    @field:NotBlank(message = "로그인 아이디를 입력해주세요") @Schema(description = "로그인 Id", pattern = "loginId")
    val userId: String,
    @field:Size(min = 10, message = "비밀번호는 최소 10자 이상이어야 합니다.") @Schema(description = "비밀번호", pattern = "hello123!!")
    val password: String
)
