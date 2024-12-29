package reservation.project.domain.payment.entity

import jakarta.persistence.*
import reservation.project.domain.reservation.entity.Reservation
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "payment")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val paymentId: Long = 0,

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    val reservation: Reservation,

    @Column(name = "payment_amount", nullable = false)
    val paymentAmount: BigDecimal,

    @Column(name = "payment_date", nullable = false)
    val paymentDate: LocalDate,

    @Column(name = "payment_status", nullable = false)
    val paymentStatus: String
)