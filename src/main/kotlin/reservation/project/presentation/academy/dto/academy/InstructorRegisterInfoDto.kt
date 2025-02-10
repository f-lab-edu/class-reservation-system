package reservation.project.presentation.academy.dto.academy


import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import reservation.project.domain.academy.entity.AcademyInstructor
import reservation.project.domain.academy.entity.InstructorRole
import java.time.LocalDateTime

data class InstructorRegisterInfoDto(
    @field:NotBlank(message = "마스터 정보가 없습니다.") @Schema(description = "마스터 유저 Id", pattern = "loginId")
    val masterUid: String,

    @Schema(description = "소속 강사 Id", pattern = "loginNormalId")
    val normalUid: String ?= null,

    @field:NotBlank(message = "학원 정보가 없습니다.") @Schema(description = "학원 Id", pattern = "1")
    val academyId: Long
){
    fun toMasterEntity(masterId: Long,role: String): AcademyInstructor {
        return AcademyInstructor(
            customerId = masterId,
            academyId = this.academyId,
            role = enumValueOf<InstructorRole>(role),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

}
