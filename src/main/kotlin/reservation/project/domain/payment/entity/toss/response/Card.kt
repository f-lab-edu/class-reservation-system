package reservation.project.domain.payment.entity.toss.response

data class Card(
    val issuerCode: String,
    val acquirerCode: String,
    val number: String,
    val installmentPlanMonths: Int,
    val isInterestFree: Boolean,
    val interestPayer: Any?,
    val approveNo: String,
    val useCardPoint: Boolean,
    val cardType: String,
    val ownerType: String,
    val acquireStatus: String,
    val receiptUrl: String,
    val amount: Int
)
