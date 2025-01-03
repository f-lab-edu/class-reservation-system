package reservation.project.domain.payment.entity

import jakarta.persistence.*
import reservation.project.domain.common.BaseEntity
import reservation.project.domain.payment.status.PaymentMethodStatus
import reservation.project.domain.payment.status.PaymentStatus
import java.math.BigDecimal

@Entity
@Table(name = "payment")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "order_id", nullable = false)
    val orderId: String,

    @Column(name = "payment_amount", nullable = false)
    val paymentAmount: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    val paymentStatus: PaymentStatus,

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    val paymentMethod: PaymentMethodStatus, // 'TOSS', 'KAKAOPAY', etc.

    @Column(name = "transaction_id")
    val transactionId: String? = null// 결제사 공통 트랜잭션 ID
): BaseEntity()