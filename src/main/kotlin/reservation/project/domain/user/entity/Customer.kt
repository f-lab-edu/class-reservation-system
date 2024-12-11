package reservation.project.domain.user.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "customer")
data class Customer(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    val id: Long=0,

    @Column(name = "charging_balance", nullable = false)
    val chargingBalance: BigDecimal,

    @Column(name = "user_name", nullable = false)
    val username: String,

    @Column(name="password", nullable = false)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name="role",nullable = false)
    val role: Role = Role.USER
)