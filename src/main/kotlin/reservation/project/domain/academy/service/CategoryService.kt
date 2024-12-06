package reservation.project.domain.academy.service

import reservation.project.domain.academy.entity.Category
import java.util.Optional

interface CategoryService {
    fun findByCategoryId(categoryId: Int): Optional<Category>
}