package reservation.project.infra.academy.impl

import org.springframework.stereotype.Component
import reservation.project.domain.academy.entity.Category
import reservation.project.domain.academy.repository.CategoryRepository
import reservation.project.infra.academy.JpaCategoryRepository
import java.util.*

@Component
class CategoryRepositoryImpl(
    private val jpaCategoryRepository: JpaCategoryRepository
): CategoryRepository {
    override fun findByCategoryId(categoryId: Int): Optional<Category> {
        return jpaCategoryRepository.findByCategoryId(categoryId)
    }
}