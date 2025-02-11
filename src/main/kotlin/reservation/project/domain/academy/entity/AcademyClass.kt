package reservation.project.domain.academy.entity

import jakarta.persistence.*
import reservation.project.domain.academy.status.ClassStatus
import reservation.project.presentation.academy.dto.academyClass.AcademyClassUpdateDto
import java.time.LocalDateTime

@Entity
@Table(name = "academy_class")
class AcademyClass(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Version
    var version: Int? = null,

    @Column(nullable = false)
    var className: String,

    @Column(nullable = false)
    var maxAppliedStudents: Int = 0, // 강의 신청 인원

    @Column(nullable = false)
    var applicationStartTime: LocalDateTime, // 강의 신청 등록 시간

    @Column(nullable = false)
    var applicationEndTime: LocalDateTime, // 강의 신청 마감 시간

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var classStatus: ClassStatus, // 강의 등록 상태

    @Column(nullable = false)
    var classStartTime: LocalDateTime, // 수업 운영 시작 시간

    @Column(nullable = false)
    var classEndTime: LocalDateTime, // 수업 운영 마감 시간

    @Column(nullable = false)
    var classDays: String, // 요일을 "MONDAY,TUESDAY" 형태로 저장

    @Column(nullable = false)
    var tuitionFee: Int, // 수업료

    @Column(nullable = false)
    var academyId: Long, // 학원 ID

    @OneToMany(mappedBy = "academyClass", fetch = FetchType.EAGER)
    var applications: MutableList<Apply> = mutableListOf()
) {

    constructor() : this(
        id = null,
        version = null,
        className = "",
        maxAppliedStudents = 0,
        applicationStartTime = LocalDateTime.now(),
        applicationEndTime = LocalDateTime.now(),
        classStatus = ClassStatus.WAITING, // 기본값 설정
        classStartTime = LocalDateTime.now(),
        classEndTime = LocalDateTime.now(),
        classDays = "",
        tuitionFee = 0,
        academyId = 0L
    )

    fun updateFromDto(updateDto: AcademyClassUpdateDto) {
        updateDto.className?.let { this.className = it }
        updateDto.maxAppliedStudents?.let { this.maxAppliedStudents = it }
        updateDto.applicationStartTime?.let { this.applicationStartTime = it }
        updateDto.applicationEndTime?.let { this.applicationEndTime = it }
        updateDto.classStatus?.let { this.classStatus = it }
        updateDto.classStartTime?.let { this.classStartTime = it }
        updateDto.classEndTime?.let { this.classEndTime = it }
        updateDto.classDays?.let { this.classDays = it }
        updateDto.tuitionFee?.let { this.tuitionFee = it }
        updateDto.academyId?.let { this.academyId = it }
    }

    fun getClassDaysList(): List<String> = classDays.split(",")
    fun setClassDaysList(days: List<String>) {
        classDays = days.joinToString(",")
    }

    fun canApply(): Boolean {
        return applications.size < maxAppliedStudents  // 현재 신청 인원 확인
    }


}
