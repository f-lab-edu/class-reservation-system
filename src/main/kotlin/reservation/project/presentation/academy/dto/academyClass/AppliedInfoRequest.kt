package reservation.project.presentation.academy.dto.academyClass

data class AppliedInfoRequest(
    val userUid: String,
    val classId: Long,
    val academyId: Long,
)
