package reservation.project.infra.order

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.order.entity.Order
import java.util.Optional

@Repository
interface JpaOrderRepository : JpaRepository<Order, Long> {
    fun findByOrderIdAndUserId(orderId: String, userId: Long): List<Order>
    fun findByUserId(userId: Long): List<Order>
    fun findByOrderIdAndUserIdAndClassId(orderId: String, userId: Long, classId: Long): Optional<Order>
}