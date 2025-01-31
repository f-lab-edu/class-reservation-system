package reservation.project.domain.payment.entity.toss.response

import java.math.BigDecimal

data class EasyPay(
    val provider: String,
    val amount: BigDecimal,
    val discountAmount: BigDecimal
)
