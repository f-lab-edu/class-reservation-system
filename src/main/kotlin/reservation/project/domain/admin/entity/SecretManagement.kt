package reservation.project.domain.admin.entity

import jakarta.persistence.*
import reservation.project.domain.academy.entity.Academy

@Entity
@Table(name = "secret_management")
data class SecretManagement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_id")
    val keyId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "academy_id", nullable = false)
    val academy: Academy,

    @Column(name = "secretkey", nullable = false)
    val secretKey: String
)