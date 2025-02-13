package reservation.project.service.academy.repository

import reservation.project.domain.academy.entity.Academy
import org.slf4j.LoggerFactory
import reservation.project.domain.academy.repository.AcademyRepository
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class AcademyFakeJpaRepository : AcademyRepository {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val repo = ConcurrentHashMap<Long, Academy>()
    private var idCounter: Long = 1L


    override fun saveAcademy(academy: Academy): Academy? {
        return if (repo.containsKey(academy.id)) {
            // 기존 엔티티 수정
            val updatedAcademy = Academy(
                id = academy.id,
                academyName = academy.academyName,
                category = academy.category,
                openTime = academy.openTime,
                closeTime = academy.closeTime,
                location = academy.location,
                socialNetworkAddress = academy.socialNetworkAddress,
                contactInfo = academy.contactInfo,
                createdAt = academy.createdAt,
                updatedAt = LocalDateTime.now() // 수정된 시간 업데이트
            )
            repo[updatedAcademy.id] = updatedAcademy
            updatedAcademy
        } else {
            // 새 엔티티 추가
            val newAcademy = Academy(
                id = idCounter++, // 새로운 ID 할당
                academyName = academy.academyName,
                category = academy.category,
                openTime = academy.openTime,
                closeTime = academy.closeTime,
                location = academy.location,
                socialNetworkAddress = academy.socialNetworkAddress,
                contactInfo = academy.contactInfo,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
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
