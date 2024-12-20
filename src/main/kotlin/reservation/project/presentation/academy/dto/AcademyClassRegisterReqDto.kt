package reservation.project.presentation.academy.dto

import jakarta.validation.constraints.NotBlank
import reservation.project.domain.academy.entity.AcademyClass
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime

data class AcademyClassRegisterReqDto(
    @field:NotBlank(message = "유효하지 않은 학원정보입니다.")
    val academyId: Long,

    @field:NotBlank(message = "유효하지 않은 유저정보입니다.")
    val customerId: Long,

    @field:NotBlank(message = "유효하지 않은 강사정보입니다.")
    val adminId: Long,

    @field:NotBlank(message = "수업명을 알려주세요")
    val className: String,

    @field:NotBlank(message = "수강 등록 인원을 알려주세요")
    val classEnrollNumber: Int,

    @field:NotBlank(message = "수강신청 시작 날짜를 알려주세요")
    val classRegistStartDate: LocalDateTime,

    @field:NotBlank(message = "수강신청 마감 날짜를 알려주세요")
    val classRegistDeadlineDate: LocalDateTime,

    @field:NotBlank(message = "수업 시작 시간을 알려주세요")
    val classStartTime: LocalTime,

    @field:NotBlank(message = "수업 끝나는 시간을 알려주세요")
    val classCloseTime: LocalTime,

    @field:NotBlank(message = "수업료를 등록해주세요")
    val classTuition: BigDecimal,

    val classInstructor: String,
){
    fun toEntity(): AcademyClass {
        return AcademyClass(
            0,
            academy = null,
            className = this.className,
            classEnrollNumber = this.classEnrollNumber,
            classRegistStartDate = this.classRegistStartDate,
            classRegistDeadlineDate = this.classRegistDeadlineDate,
            classStartTime = this.classStartTime,
            classCloseTime = this.classCloseTime,
            classTuition = this.classTuition,
            classInstructor = this.classInstructor,
            customerId = this.customerId,
            adminId = this.adminId,
            status = null
        )
    }
}
