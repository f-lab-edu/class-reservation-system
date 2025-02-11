package reservation.project.service.academy.repository

import org.slf4j.LoggerFactory
import reservation.project.domain.academy.entity.Academy
import reservation.project.domain.academy.repository.AcademyRepository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class AcademyFakeJpaRepository : AcademyRepository {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val repo = ConcurrentHashMap<Long, Academy>()
    private var idCounter: Long = 1L


    override fun saveAcademy(academy: Academy): Academy? {
        return if (repo.containsKey(academy.id)) {
            val updatedAcademy = academy.copy(id = academy.id)  // id를 그대로 유지하면서 복사
            repo[updatedAcademy.id] = updatedAcademy
            updatedAcademy
        } else {
            val newAcademy = academy.copy(id = idCounter++)  // 새로운 id를 할당해서 삽입
            repo[newAcademy.id] = newAcademy
            newAcademy
        }
    }

    override fun findAcademyById(id: Long): Optional<Academy> {
        return Optional.ofNullable(repo[id])
    }

    override fun findByAcademyName(name: String): List<Academy> {
        return repo.values.filter { it.academyName == name }
    }


}