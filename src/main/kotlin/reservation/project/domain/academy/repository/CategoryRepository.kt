package reservation.project.domain.academy.repository

import reservation.project.domain.academy.entity.Category
import java.util.Optional

interface CategoryRepository {
    fun findByCategoryId(categoryId: Int): Optional<Category>
}