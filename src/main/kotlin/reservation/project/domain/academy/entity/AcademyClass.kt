package reservation.project.domain.academy.entity

import jakarta.persistence.*
import reservation.project.domain.academy.status.ClassStatus
import reservation.project.presentation.academy.dto.AcademyClassUpdateReqDto
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = "academy_class")
data class AcademyClass(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val classId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "academy_id", nullable = false)
    var academy: Academy?,

    @Column(name = "class_name", nullable = false)
    var className: String,

    @Column(name = "capacity", nullable = false)
    var capacity: Int,

    @Column(name = "class_regist_start_date", nullable = false)
    var classRegistStartDate: LocalDateTime,

    @Column(name = "class_regist_deadline_date", nullable = false)
    var classRegistDeadlineDate: LocalDateTime,

    @Column(name = "class_start_time", nullable = false)
    var classStartTime: LocalTime,

    @Column(name = "class_close_time", nullable = false)
    var classCloseTime: LocalTime,

    @Column(name = "class_tuition", nullable = false)
    var classTuition: BigDecimal,

    @Column(name = "class_instructor", nullable = false)
    var classInstructor: String,

    @Column(name = "id", nullable = false)
    var adminId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: ClassStatus? = null
){
    fun isCapacityExceeded(registeredStudents: Int): Boolean{
        return capacity<=registeredStudents
    }

    fun changeClassStatusToWAITING(){
        this.status = ClassStatus.WAITING
    }

    fun toUpdateAcademyClass(req: AcademyClassUpdateReqDto) {
        this.adminId = req.adminId
        this.className = req.className
        this.capacity = req.capacity
        this.classRegistStartDate = req.classRegistStartDate
        this.classRegistDeadlineDate = req.classRegistDeadlineDate
        this.classStartTime = req.classStartTime
        this.classCloseTime = req.classCloseTime
        this.classTuition = req.classTuition
        this.classInstructor = req.classInstructor
    }
}