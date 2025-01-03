package reservation.project.domain.order.service

import org.springframework.stereotype.Service
import reservation.project.domain.order.entity.Order
import reservation.project.infra.order.JpaOrderRepository
import java.util.*

@Service
class OrderService(
    private val jpaOrderRepository: JpaOrderRepository
) {

    fun findGetClassInfo(orderId: String, userId: Long): List<Order> {
        return jpaOrderRepository.findByOrderIdAndUserId(orderId, userId)
    }

    fun findOrderInfoByUserId(userId: Long): List<Order> {
        return jpaOrderRepository.findByUserId(userId)
    }

    fun findOrderInfo(orderId: String, userId: Long, classId: Long): Optional<Order>{
        return jpaOrderRepository.findByOrderIdAndUserIdAndClassId(orderId, userId, classId)
    }
}