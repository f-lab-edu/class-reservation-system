package reservation.project.domain.academy.entity

import jakarta.persistence.*

@Entity
@Table(name = "category")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val categoryId: Long = 0,

    @Column(name = "category_name", nullable = false)
    val categoryName: String
)