package reservation.project.domain.academy.service

import reservation.project.domain.academy.entity.Academy
import java.util.*

interface AcademyService {

    fun findByAcademyId(academyId: Int): Optional<Academy>
    fun save(academy: Academy): Optional<Academy>
    fun update(academy: Academy): Optional<Academy>
}