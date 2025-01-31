package reservation.project.presentation.academy.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import reservation.project.domain.academy.entity.Academy
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class AcademyRegisterInfoDto(
    @field:NotBlank(message = "유저 아이디를 입력해주세요") @Schema(description = "유저 Id", pattern = "loginId")
    val uid: String,

    @field:NotBlank(message = "학원이름을 입력해주세요") @Schema(description = "학원이름", pattern = "FiveSpot")
    val academyName: String,

    @field:NotNull(message = "카테고리를 선택해주세요") @Schema(description = "카테고리", pattern = "1")
    val category: Long,

    @Schema(description = "Open 시간", pattern = "2025/01/25 15:30")
    val openTime: String = "",

    @Schema(description = "카테고리", pattern = "2025/01/25 20:30")
    val closeTime: String = "",

    @field:NotBlank(message = "장소를 입력해주세요") @Schema(description = "장소 정보", pattern = "서울시 땡땡구")
    val location: String,

    @Schema(description = "Social Url(인스타 등)", pattern = "")
    val socialNetworkAddress: String = "",

    @field:NotBlank(message = "연락처를 입력해주세요") @Schema(description = "연락처 정보(핸드폰 or 가게주소)", pattern = "01022223333")
    val contactInfo: String = "",

    @field:NotBlank(message = "학원에서의 역할을 입력해주세요") @Schema(description = "MASTER/NORMAL", pattern = "MASTER")
    val role: String
) {
    fun toEntity(): Academy {
        return Academy(
            id = 0,
            academyName = this.academyName,
            category = this.category,
            openTime = convertDateTime(this.openTime),
            closeTime = convertDateTime(this.closeTime),
            location = this.location,
            socialNetworkAddress = this.socialNetworkAddress,
            contactInfo = this.contactInfo,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    private fun convertDateTime(value: String): LocalDateTime {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
    }
}
