package reservation.project.presentation.academy.dto

import reservation.project.domain.academy.entity.Academy


data class AcademyUpdateDto(
    var academyName: String?,
    val category: Long?,
    val openTime: String?,
    val closeTime: String?,
    val location: String?,
    val socialNetworkAddress: String?,
    val contactInfo: String?,
    val info: Academy
)
