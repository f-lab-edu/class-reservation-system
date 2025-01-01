package reservation.project.domain.payment.service.impl

import org.springframework.stereotype.Service
import reservation.project.infra.payment.toss.JpaTossPaymentRepository

@Service
class TossPaymentServiceImpl(
    private val jpaTossPaymentRepository: JpaTossPaymentRepository
)  {
     fun createPayment(): String {
        TODO("Not yet implemented")
    }

     fun handlePaymentSuccess(): String {
        TODO("Not yet implemented")
    }

     fun handlePaymentCancel(): String {
        TODO("Not yet implemented")
    }

}