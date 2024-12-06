package reservation.project.domain.reservation.entity

import jakarta.persistence.*
import reservation.project.domain.academy.entity.AcademyClass
import java.time.LocalDate

@Entity
@Table(name = "reservation")
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    val reservationId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    val academyClass: AcademyClass,

    @Column(name = "user_id", nullable = false)
    val userId: Int,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDate,

    @Column(name = "status", nullable = false)
    val status: String,

    @Column(name = "updated_at")
    val updatedAt: LocalDate? = null
)