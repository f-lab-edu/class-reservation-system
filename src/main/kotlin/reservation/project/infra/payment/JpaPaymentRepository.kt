package reservation.project.infra.payment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.payment.entity.Payment
import java.util.*

@Repository
interface JpaPaymentRepository : JpaRepository<Payment, Long>{

    fun findByApplyId(applyId: Long): Optional<Payment>
    fun findByOrderId(orderId: Long): Optional<Payment>

}