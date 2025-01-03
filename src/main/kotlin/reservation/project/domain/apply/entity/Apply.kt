package reservation.project.domain.apply.entity

import jakarta.persistence.*
import reservation.project.domain.apply.status.ApplyStatus
import reservation.project.domain.common.BaseEntity
import java.time.LocalDate

@Entity
@Table(name = "apply")
data class Apply(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long ? = null,


    @Column(name = "class_id", nullable = false)
    val classId: Long,

    @Column(name = "user_id", nullable = false)
    val userId: Long,


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: ApplyStatus,
): BaseEntity()