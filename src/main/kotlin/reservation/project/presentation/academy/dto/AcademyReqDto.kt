package reservation.project.presentation.academy.dto

import jakarta.validation.constraints.NotBlank
import reservation.project.domain.academy.entity.Academy
import java.time.LocalDateTime

data class AcademyReqDto(
    @field:NotBlank(message = "학원 이름을 알려주세요")
    val academyName: String,

    @field:NotBlank(message = "학원 연락처 정보를 입력해주세요")
    val contactInfo: String,

    @field:NotBlank(message = "학원 위치 정보를 입력해주세요")
    val location: String,

    @field:NotBlank(message = "학원 운영(open)를 입력해주세요")
    val openTime: LocalDateTime,

    @field:NotBlank(message = "학원 운영(close)를 입력해주세요")
    val closeTime: LocalDateTime,

    val socialNetworkAddress: String,

    val categoryId: Int
){
    fun toEntity(): Academy {
        return Academy(
            0,
            academyName = this.academyName,
            category = null,
            openTime = this.openTime,
            location = this.location,
            socialNetworkAddress = this.socialNetworkAddress,
            rating = 0.0,
            contactInfo = this.contactInfo
        )
    }
}
