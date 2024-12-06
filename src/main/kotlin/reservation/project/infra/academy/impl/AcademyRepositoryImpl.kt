package reservation.project.infra.academy.impl

import org.springframework.stereotype.Component
import reservation.project.domain.academy.entity.Academy
import reservation.project.domain.academy.repository.AcademyRepository
import java.util.*

@Component
class AcademyRepositoryImpl(
    private val jpaAcademyRepository: AcademyRepository
) : AcademyRepository {
    override fun findByAcademyId(academyId: Int): Optional<Academy> {
        return jpaAcademyRepository.findByAcademyId(academyId)
    }

    override fun save(academy: Academy): Optional<Academy> {
        return jpaAcademyRepository.save(academy)
    }

    override fun update(academy: Academy): Optional<Academy> {
        return jpaAcademyRepository.save(academy)
    }
}