package reservation.project.domain.academy.repository.impl

import org.apache.catalina.connector.Response
import org.springframework.stereotype.Component
import reservation.project.domain.academy.entity.Academy
import reservation.project.domain.academy.repository.AcademyRepository
import reservation.project.infra.academy.AcademyJpaRepository
import java.util.*


@Component
class AcademyRepositoryImpl(
    private val academyJpaRepository: AcademyJpaRepository
): AcademyRepository {

    override fun saveAcademy(academy: Academy): Academy? {
        return academyJpaRepository.save(academy)
    }

    override fun findAcademyById(id: Long): Optional<Academy> {
        return academyJpaRepository.findById(id)
    }

    override fun findByAcademyName(name: String): List<Academy> {
        return academyJpaRepository.findByAcademyName(name)
    }
}