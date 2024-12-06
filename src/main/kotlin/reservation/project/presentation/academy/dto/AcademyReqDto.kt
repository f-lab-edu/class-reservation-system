package reservation.project.presentation.academy.dto

import reservation.project.domain.academy.entity.Academy
import java.time.LocalDateTime

data class AcademyReqDto(
    val academyName: String,
    val contactInfo: String,
    val location: String,
    val openTime: LocalDateTime,
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
