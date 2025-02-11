package reservation.project.infra.payment.toss

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface JpaTossPaymentRepository
//    : JpaRepository<TossPayment, Long>
{

//    fun findByPaymentId(paymentId: Long): Optional<TossPayment>
}
