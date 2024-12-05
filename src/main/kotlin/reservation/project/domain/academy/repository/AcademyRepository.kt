package reservation.project.domain.academy.repository

import reservation.project.domain.academy.entity.Academy
import java.util.Optional

interface AcademyRepository {
    fun findByAcademyId(academyId: Int): Optional<Academy>
    fun save(academy: Academy): Optional<Academy>
    fun update(academy: Academy): Optional<Academy>
}