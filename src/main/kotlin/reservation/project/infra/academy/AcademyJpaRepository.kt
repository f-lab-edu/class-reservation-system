package reservation.project.infra.academy

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.academy.entity.Academy
import java.util.*

@Repository
interface AcademyJpaRepository : JpaRepository<Academy, Long> {

    fun save(academy: Academy): Academy?
    fun findByAcademyName(academyName: String): List<Academy>
    override fun findById(id: Long): Optional<Academy>
}