package reservation.project.domain.payment.entity.toss

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import reservation.project.domain.common.BaseEntity
import java.time.LocalDateTime

data class TossPayment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "payment_id", nullable = false)
    val paymentId: Long,

    @Column(name = "payment_key", nullable = false)
    val paymentKey: String,

    @Column(name = "approved_at", nullable = false)
    val approvedAt: LocalDateTime?= null,

    @Column(name = "canceled_at", nullable = false)
    val canceledAt: LocalDateTime? = null,
): BaseEntity()
