package reservation.project.domain.token.entity

import jakarta.persistence.*
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.domain.user.entity.Customer
import java.time.LocalDateTime

@Entity
@Table(name = "token")
data class Token(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "access_token", nullable = false)
    val accessToken: String,

    @Column(name = "expired_at", nullable = false)
    val expiredAt: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    val customer: Customer,

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    val academyClass: AcademyClass,

    @Column(name = "status", nullable = false)
    val status: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime
)
