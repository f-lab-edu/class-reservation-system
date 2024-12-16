package reservation.project.infra.academy

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.academy.entity.Academy
import java.util.Optional

@Repository
interface JpaAcademyRepository : JpaRepository<Academy, Long>{

    fun findByAcademyId(academyId: Long): Optional<Academy>
    fun save(academy: Academy): Optional<Academy>
}