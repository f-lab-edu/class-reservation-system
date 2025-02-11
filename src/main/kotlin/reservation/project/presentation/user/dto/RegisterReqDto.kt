package reservation.project.presentation.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class RegisterReqDto(

    @field:NotBlank(message = "아이디를 입력해주세요") @Schema(description = "로그인 Id", pattern = "loginId")
    val id: String,
    @field:NotBlank(message = "사용하실 이름을 입력해주세요") @Schema(description = "사용자 이름", pattern = "username")
    val username: String,
    @field:Size(min = 10, message = "비밀번호는 최소 10자 이상이어야 합니다.") @Schema(description = "비밀번호", pattern = "hello123!!")
    val password: String,
    @field:NotBlank(message = "역할 구분을 입력해주세요") @Schema(description = "역할(USER/ADMIN)", pattern = "USER")
    val role: String
)
