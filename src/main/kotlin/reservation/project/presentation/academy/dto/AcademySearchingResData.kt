package reservation.project.presentation.academy.dto

import reservation.project.domain.academy.entity.Academy
import java.time.LocalDateTime

data class AcademySearchingResData(
    val academyName: String?,
    val contactInfo: String?,
    val location: String?,
    val openTime: LocalDateTime?,
    val socialNetworkAddress: String?,
    val categoryId: Int?,
    val categoryName: String?
){
    companion object{
        fun of(academy: Academy): AcademySearchingResData {
            return AcademySearchingResData(
                    academyName = academy.academyName,
                    contactInfo = academy.contactInfo.orEmpty(),
                    location = academy.location.orEmpty(),
                    openTime = academy.openTime,
                    socialNetworkAddress = academy.socialNetworkAddress,
                    categoryId = academy.category?.categoryId,
                    categoryName = academy.category?.categoryName

                    )
        }
    }
}
