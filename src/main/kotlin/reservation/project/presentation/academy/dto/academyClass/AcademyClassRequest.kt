package reservation.project.presentation.academy.dto.academyClass

import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.academy.status.ClassStatus
import java.time.LocalDateTime

data class AcademyClassRequest(
    val className: String, // 강의 이름
    val maxAppliedStudents: Int, // 강의 신청 최대 인원
    val applicationStartTime: LocalDateTime, // 강의 신청 시작 시간
    val applicationEndTime: LocalDateTime, // 강의 신청 종료 시간
    val classStartTime: LocalDateTime, // 강의 시작 시간
    val classEndTime: LocalDateTime, // 강의 종료 시간
    val classDays: List<String>, // 강의 요일 (예: MONDAY, TUESDAY)
    val tuitionFee: Int, // 수업료
    val academyId: Long,
    val customerUid: String,
){
    fun toEntity(classStatus: ClassStatus): AcademyClass {
        return AcademyClass(
            className = this.className,
            maxAppliedStudents = this.maxAppliedStudents,
            applicationStartTime = this.applicationStartTime,
            applicationEndTime = this.applicationEndTime,
            classStartTime = this.classStartTime,
            classEndTime = this.classEndTime,
            classStatus = classStatus,
            classDays = this.classDays.joinToString(","), // List -> String 변환
            tuitionFee = this.tuitionFee,
            academyId = this.academyId
        )
    }
}
