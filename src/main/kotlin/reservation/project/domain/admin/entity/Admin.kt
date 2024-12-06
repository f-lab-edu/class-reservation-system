package reservation.project.domain.admin.entity

import jakarta.persistence.*
import reservation.project.domain.academy.entity.Academy
import reservation.project.domain.user.entity.Role

@Entity
@Table(name = "admin")
data class Admin(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    val adminId: Int = 0,

    @Column(name = "admin_name")
    val adminName: String? = null,

    @Column(name = "password")
    val password: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name="role",nullable = false)
    val role: Role = Role.ADMIN,

    @ManyToOne
    @JoinColumn(name = "academy_id", nullable = false)
    val academy: Academy?
){
    constructor() : this(0, "", "", Role.ADMIN, null)
}