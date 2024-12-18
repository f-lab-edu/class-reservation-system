package reservation.project.domain.academy.entity

import jakarta.persistence.*
import reservation.project.domain.user.entity.Customer
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = "academy_class")
data class AcademyClass(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val classId: Long = 0,

    @ManyToOne
    @JoinColumn(name = "academy_id", nullable = false)
    val academy: Academy,

    @Column(name = "class_name", nullable = false)
    val className: String,

    @Column(name = "class_enroll_number", nullable = false)
    val classEnrollNumber: Int,

    @Column(name = "class_regist_start_date", nullable = false)
    val classRegistStartDate: LocalDateTime,

    @Column(name = "class_regist_deadline_date", nullable = false)
    val classRegistDeadlineDate: LocalDateTime,

    @Column(name = "class_start_time", nullable = false)
    val classStartTime: LocalTime,

    @Column(name = "class_close_time", nullable = false)
    val classCloseTime: LocalTime,

    @Column(name = "class_tuition", nullable = false)
    val classTuition: BigDecimal,

    @Column(name = "class_instructor", nullable = false)
    val classInstructor: String,

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    val customer: Customer,

    @Column(name = "id", nullable = false)
    val adminId: Long,

    @Column(name = "status")
    val status: String? = null
)