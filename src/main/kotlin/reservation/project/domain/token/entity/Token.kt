package reservation.project.domain.token.entity

import jakarta.persistence.*
import reservation.project.domain.token.status.TokenStatus
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
    val expiredAt: LocalDateTime?,

    @Column(name = "customerId", nullable = false)
    val customerId: Long,

    @Column(name = "academyClassId", nullable = false)
    val academyClassId: Long,

    @Column(name = "status", nullable = false)
    val status: TokenStatus,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime?
) {
    fun isQueueCanceled(): Boolean {
        return this.status == TokenStatus.CANCELLED
    }
}
