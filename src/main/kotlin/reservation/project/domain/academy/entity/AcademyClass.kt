package reservation.project.domain.academy.entity

import jakarta.persistence.*
import reservation.project.domain.user.entity.Customer
import java.time.LocalDate

@Entity
@Table(name = "academy_class")
data class AcademyClass(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val classId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "academy_id", nullable = false)
    val academy: Academy,

    @Column(name = "class_name", nullable = false)
    val className: String,

    @Column(name = "class_enroll_number", nullable = false)
    val classEnrollNumber: String,

    @Column(name = "class_regist_start_date", nullable = false)
    val classRegistStartDate: LocalDate,

    @Column(name = "class_regist_deadline_date", nullable = false)
    val classRegistDeadlineDate: LocalDate,

    @Column(name = "class_start_time", nullable = false)
    val classStartTime: LocalDate,

    @Column(name = "class_close_time", nullable = false)
    val classCloseTime: LocalDate,

    @Column(name = "class_tuition", nullable = false)
    val classTuition: Int,

    @Column(name = "class_instructor", nullable = false)
    val classInstructor: String,

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    val customer: Customer,

    @Column(name = "id", nullable = false)
    val adminId: Int,

    @Column(name = "status")
    val status: String? = null
)