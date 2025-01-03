package reservation.project.domain.order.entity

import jakarta.persistence.*
import reservation.project.domain.common.BaseEntity
import reservation.project.domain.order.status.OrderStatus
import java.math.BigDecimal

@Entity
@Table(name = "order")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "order_id", nullable = false)
    val orderId: String,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "class_id", nullable = false)
    val classId: Long,

    @Column(name = "total_amount", nullable = false)
    val totalAmount: BigDecimal,     // 결제 금액

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: OrderStatus,   // 결제 상태 (PENDING, COMPLETED)
): BaseEntity()
