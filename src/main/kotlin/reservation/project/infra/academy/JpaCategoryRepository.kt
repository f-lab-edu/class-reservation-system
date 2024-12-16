package reservation.project.infra.academy

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.academy.entity.Category
import java.util.Optional

@Repository
interface JpaCategoryRepository : JpaRepository<Category, Long>{

    fun findByCategoryId(categoryId: Long): Optional<Category>
}