package reservation.project.domain.customer.entity

import jakarta.persistence.*

@Entity
@Table(name = "customer_payment_info")
data class CustomerPaymentInfo(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long=0,

    @Column(name = "client_key", nullable = false)
    val clientKey: String,

    @Column(name = "secret_key", nullable = false)
    val secretKey: String,

    @Column(name = "customer_id", nullable = false)
    val customerId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name="payment_method",nullable = false)
    val paymentMethod: PaymentMethod
)
