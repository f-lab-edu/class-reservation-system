package reservation.project.domain.academy.entity

import jakarta.persistence.*

@Entity
@Table(name = "apply")
class Apply(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "academy_class_id")
    var academyClass: AcademyClass,

    var customerId: Long // 신청한 고객 ID
) {
    constructor() : this(id = null, academyClass = AcademyClass(), customerId = 0L)
}
