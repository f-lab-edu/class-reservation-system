package reservation.project.presentation.academy.dto.academyClass

import reservation.project.domain.academy.status.ClassStatus
import java.time.LocalDateTime

data class AcademyClassUpdateDto(
    val academyClassId: Long?,
    val className: String?,
    val maxAppliedStudents: Int?,
    val applicationStartTime: LocalDateTime?,
    val applicationEndTime: LocalDateTime?,
    val classStatus: ClassStatus?,
    val classStartTime: LocalDateTime?,
    val classEndTime: LocalDateTime?,
    val classDays: String?,
    val tuitionFee: Int?,
    val academyId: Long?
)
