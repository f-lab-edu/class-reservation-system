package reservation.project.domain.academy.service

import org.springframework.stereotype.Service
import reservation.project.domain.academy.entity.Academy
import reservation.project.infra.academy.JpaAcademyRepository
import java.util.*

@Service
class AcademyService(
    private val jpaAcademyRepository : JpaAcademyRepository,
)  {
     fun findByAcademyId(academyId: Int): Optional<Academy> {
        return jpaAcademyRepository.findByAcademyId(academyId)
    }

     fun save(academy: Academy): Optional<Academy> {
        return jpaAcademyRepository.save(academy)
    }

     fun update(academy: Academy): Optional<Academy> {
        return jpaAcademyRepository.save(academy)
    }
}