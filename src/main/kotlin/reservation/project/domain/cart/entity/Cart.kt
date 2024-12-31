package reservation.project.domain.cart.entity

import jakarta.persistence.*
import reservation.project.domain.academy.status.ClassStatus
import reservation.project.domain.common.BaseEntity

@Entity
@Table(name = "cart")
data class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long ?= null,

    @Column(name="class_id", nullable = false)
    val classId: Long,

    @Column(name="user_id", nullable = false)
    val userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name="class_status", nullable = false)
    val classStatus: ClassStatus,

):BaseEntity()
