package reservation.project.domain.academy.service

import org.springframework.stereotype.Service
import reservation.project.domain.academy.entity.Category
import reservation.project.infra.academy.JpaCategoryRepository
import java.util.*

@Service
class CategoryService(
    private val jpaCategoryRepository : JpaCategoryRepository,
) {
     fun findByCategoryId(categoryId: Long): Optional<Category> {
        return jpaCategoryRepository.findByCategoryId(categoryId)
    }
}