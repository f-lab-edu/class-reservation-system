package reservation.project.domain.academy.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "academy")
data class Academy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "academy_id")
    val academyId: Int = 0,

    @Column(name = "academy_name", nullable = false)
    val academyName: String,

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    var category: Category?=null,

    @Column(name = "open_time")
    val openTime: LocalDateTime? = null,

    @Column(name = "location")
    val location: String? = null,

    @Column(name = "social_network_address")
    val socialNetworkAddress: String? = null,

    @Column(name = "rating")
    val rating: Double? = null,

    @Column(name = "contact_info")
    val contactInfo: String? = null
)