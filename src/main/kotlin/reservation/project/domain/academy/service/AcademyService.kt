package reservation.project.domain.academy.service

import org.springframework.stereotype.Service
import reservation.project.domain.academy.entity.Academy
import reservation.project.domain.academy.repository.AcademyRepository
import java.util.*

@Service
class AcademyService(
    private val academyRepository: AcademyRepository
)  {
     fun findByAcademyId(academyId: Int): Optional<Academy> {
        return academyRepository.findByAcademyId(academyId)
    }

     fun save(academy: Academy): Optional<Academy> {
        return academyRepository.save(academy)
    }

     fun update(academy: Academy): Optional<Academy> {
        return academyRepository.update(academy)
    }
}