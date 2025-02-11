package reservation.project.domain.academy.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "academy")
data class Academy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "academy_name", nullable = false)
    var academyName: String,

    @Column(name = "category_id", nullable = false)
    var category: Long = 0,

    @Column(name = "open_time")
    var openTime: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "close_time")
    var closeTime: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "location")
    var location: String? = "",

    @Column(name = "social_network_address")
    var socialNetworkAddress: String? = "",

//    @Column(name = "rating")
//    val rating: Double? = 0.0,

    @Column(name = "contact_info")
    var contactInfo: String? = "",

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = LocalDateTime.now()
)