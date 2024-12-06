package reservation.project.domain.user.entity

import jakarta.persistence.*

@Entity
@Table(name = "customer")
data class Customer(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    val id: Long=0,

    @Column(name = "charging_balance", nullable = false)
    val chargingBalance: Double = 0.0,

    @Column(name = "user_name", nullable = false)
    val username: String,

    @Column(name="password", nullable = false)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name="role",nullable = false)
    val role: Role = Role.USER
){
    constructor() : this(0, 0.0, "", "", Role.USER)
}