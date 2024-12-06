package reservation.project.domain.academy.service.impl

import org.springframework.stereotype.Service
import reservation.project.domain.academy.entity.Category
import reservation.project.domain.academy.repository.CategoryRepository
import reservation.project.domain.academy.service.CategoryService
import java.util.*

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository
): CategoryService {
    override fun findByCategoryId(categoryId: Int): Optional<Category> {
        return categoryRepository.findByCategoryId(categoryId)
    }
}