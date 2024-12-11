package reservation.project.domain.academy.entity

import jakarta.persistence.*

@Entity
@Table(name = "secret_management")
data class SecretManagement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val keyId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    val academy: Academy,

    @Column(name = "secretkey", nullable = false)
    val secretKey: String
)