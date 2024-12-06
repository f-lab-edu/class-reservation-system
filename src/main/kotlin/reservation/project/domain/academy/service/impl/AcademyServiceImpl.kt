package reservation.project.domain.academy.service.impl

import org.springframework.stereotype.Service
import reservation.project.domain.academy.entity.Academy
import reservation.project.domain.academy.repository.AcademyRepository
import reservation.project.domain.academy.service.AcademyService
import java.util.*

@Service
class AcademyServiceImpl(
    private val academyRepository: AcademyRepository
) : AcademyService {
    override fun findByAcademyId(academyId: Int): Optional<Academy> {
        return academyRepository.findByAcademyId(academyId)
    }

    override fun save(academy: Academy): Optional<Academy> {
        return academyRepository.save(academy)
    }

    override fun update(academy: Academy): Optional<Academy> {
        return academyRepository.update(academy)
    }
}