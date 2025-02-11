package reservation.project.presentation.academy.dto.academy

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import reservation.project.domain.academy.entity.Academy

data class AcademyUpdateInfoReqDto(
    @field:NotBlank(message = "유저정보가 존재하지 않습니다.") @Schema(description = "유저 Id", pattern = "loginId")
    val uid: String,

    @field:NotNull(message = "유저정보가 존재하지 않습니다.") @Schema(description = "유저 Id", pattern = "loginId")
    val academyId: Long,

    @Schema(description = "학원이름", pattern = "FiveSpot")
    val academyName: String? = null,

    @Schema(description = "카테고리", pattern = "1")
    val category: Long? = null,

    @Schema(description = "Open 시간", pattern = "2025/01/25 15:30")
    val openTime: String? = null,

    @Schema(description = "카테고리", pattern = "2025/01/25 20:30")
    val closeTime: String? = null,

    @Schema(description = "장소 정보", pattern = "서울시 땡땡구")
    val location: String? = null,

    @Schema(description = "Social Url(인스타 등)", pattern = "")
    val socialNetworkAddress: String? = null,

    @Schema(description = "연락처 정보(핸드폰 or 가게주소)", pattern = "01022223333")
    val contactInfo: String? = null,
){
    fun toUpdateDto(academyInfo: Academy) : AcademyUpdateDto {
        return AcademyUpdateDto(
            academyName = this.academyName,
            category = this.category,
            openTime = this.openTime,
            closeTime = this.closeTime,
            location = this.location,
            socialNetworkAddress = this.socialNetworkAddress,
            contactInfo = this.contactInfo,
            info = academyInfo
        )
    }


}
