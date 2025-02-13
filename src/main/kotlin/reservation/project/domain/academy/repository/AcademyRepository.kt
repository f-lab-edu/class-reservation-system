package reservation.project.domain.academy.repository

import reservation.project.domain.academy.entity.Academy
import java.util.*

interface AcademyRepository {

    fun saveAcademy(academy: Academy): Academy?

    fun findAcademyById(id: Long): Optional<Academy>

    fun findByAcademyName(name: String): List<Academy>
}
