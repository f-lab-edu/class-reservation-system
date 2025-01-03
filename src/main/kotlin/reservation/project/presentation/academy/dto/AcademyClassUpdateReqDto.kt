package reservation.project.presentation.academy.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import reservation.project.domain.academy.entity.AcademyClass
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime

data class AcademyClassUpdateReqDto(
    @field:NotBlank(message = "유효하지 않은 클래스 정보입니다.")
    val classId: Long,

    @field:NotNull(message = "유효하지 않은 학원정보입니다.")
    val academyId: Long,

    @field:NotNull(message = "유효하지 않은 강사정보입니다.")
    val adminId: Long,

    @field:NotBlank(message = "수업명을 알려주세요")
    val className: String,

    @field:NotNull(message = "수강 등록 인원을 알려주세요")
    val capacity: Int,

    @field:NotNull(message = "수강신청 시작 날짜를 알려주세요")
    val classRegistStartDate: LocalDateTime,

    @field:NotNull(message = "수강신청 마감 날짜를 알려주세요")
    val classRegistDeadlineDate: LocalDateTime,

    @field:NotNull(message = "수업 시작 시간을 알려주세요")
    val classStartTime: LocalTime,

    @field:NotNull(message = "수업 끝나는 시간을 알려주세요")
    val classCloseTime: LocalTime,

    @field:NotNull(message = "수업료를 등록해주세요")
    val classTuition: BigDecimal,

    val classInstructor: String,
){
    fun toEntity(): AcademyClass {
        return AcademyClass(
            classId = this.classId,
            academy = null,
            className = this.className,
            capacity = this.capacity,
            classRegistStartDate = this.classRegistStartDate,
            classRegistDeadlineDate = this.classRegistDeadlineDate,
            classStartTime = this.classStartTime,
            classCloseTime = this.classCloseTime,
            classTuition = this.classTuition,
            classInstructor = this.classInstructor,
            adminId = this.adminId
        )
    }
}
