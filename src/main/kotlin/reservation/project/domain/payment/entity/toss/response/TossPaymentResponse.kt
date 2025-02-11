package reservation.project.domain.payment.entity.toss.response

import java.math.BigDecimal

data class TossPaymentResponse(
    val version: String,
    val paymentKey: String,
    val type: String,
    val orderId: String,
    val orderName: String,
    val mId: String,
    val currency: String,
    val method: String,
    val totalAmount: BigDecimal,
    val balanceAmount: BigDecimal,
    val status: String,
    val requestedAt: String,
    val approvedAt: String,
    val receipt: Receipt?,
    val country: String,
    val failure: Failure?,


    val useEscrow: Boolean,
    val lastTransactionKey: String,
    val suppliedAmount: BigDecimal,
    val vat: BigDecimal,
    val cultureExpense: Boolean,
    val taxFreeAmount: BigDecimal,
    val taxExemptionAmount: BigDecimal,
    val isPartialCancelable: Boolean,
    val card: Card?,
    val secret: String,
    val checkout: CheckOut?,
    val easyPay: EasyPay?,
    val discount: Discount?,
) {
//    fun toResponse(): PaymentResponse {
//        return PaymentResponse(
//            version,
//            paymentKey,
//            type,
//            orderId,
//            orderName,
//            mId,
//            currency,
//            method,
//            totalAmount,
//            status,
//            requestedAt,
//            approvedAt,
//            receipt,
//            country,
//            failure,
//        )
//    }
}
