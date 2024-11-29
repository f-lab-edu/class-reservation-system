package reservation.project.domain.payment.entity

import jakarta.persistence.*
import reservation.project.domain.reservation.entity.Reservation
import java.time.LocalDate

@Entity
@Table(name = "payment")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    val paymentId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    val reservation: Reservation,

    @Column(name = "payment_amount", nullable = false)
    val paymentAmount: Int,

    @Column(name = "payment_date", nullable = false)
    val paymentDate: LocalDate,

    @Column(name = "payment_status", nullable = false)
    val paymentStatus: String
)