package reservation.project.domain.academy.service

import org.springframework.stereotype.Service
import reservation.project.domain.academy.entity.Category
import reservation.project.domain.academy.repository.CategoryRepository
import java.util.*

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
     fun findByCategoryId(categoryId: Int): Optional<Category> {
        return categoryRepository.findByCategoryId(categoryId)
    }
}