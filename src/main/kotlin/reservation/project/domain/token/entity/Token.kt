package reservation.project.domain.token.entity

import jakarta.persistence.*
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.user.entity.Customer
import java.time.LocalDate

@Entity
@Table(name = "token")
data class Token(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    val tokenId: Int = 0,

    @Column(name = "access_token", nullable = false)
    val accessToken: String,

    @Column(name = "expired_at", nullable = false)
    val expiredAt: Int,

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    val customer: Customer,

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    val academyClass: AcademyClass,

    @Column(name = "status", nullable = false)
    val status: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDate
)
