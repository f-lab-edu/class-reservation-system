package reservation.project.domain.academy.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "academy_instructor")
data class AcademyInstructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "customer_id")
    val customerId: Long = 0,

    @Column(name = "academy_id")
    val academyId: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: InstructorRole = InstructorRole.NORMAL,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = LocalDateTime.now()
)

