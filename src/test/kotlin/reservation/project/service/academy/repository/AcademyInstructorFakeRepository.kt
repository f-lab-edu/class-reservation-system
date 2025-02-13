package reservation.project.service.academy.repository


import org.slf4j.LoggerFactory
import reservation.project.domain.academy.entity.AcademyInstructor
import reservation.project.domain.academy.repository.AcademyInstructorRepository
import reservation.project.presentation.advice.exception.ErrorException
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class AcademyInstructorFakeRepository : AcademyInstructorRepository {
    private val repo = ConcurrentHashMap<Long, AcademyInstructor>()
    private var idCounter: Long = 1L
    private val logger = LoggerFactory.getLogger(AcademyInstructorFakeRepository::class.java)

    override fun saveInfo(academyInstructor: AcademyInstructor): AcademyInstructor? {
        val savedEntity = AcademyInstructor(
            id = idCounter++,  // 새로운 ID 할당
            customerId = academyInstructor.customerId,
            academyId = academyInstructor.academyId,
            role = academyInstructor.role,
            createdAt = academyInstructor.createdAt ?: LocalDateTime.now(), // 기존 값이 없으면 현재 시간 사용
            updatedAt = LocalDateTime.now() // 업데이트 시간 갱신
        )

        repo[savedEntity.id] = savedEntity
        return savedEntity
    }

    override fun findAcademyInstructorInfoById(id: Long): Optional<AcademyInstructor> {
        return Optional.of(repo[id]?: throw ErrorException(404, "AcademyInstructor Not Found"))
    }

    override fun findAcademyInstructorInfoByCustomerId(customerId: Long): List<AcademyInstructor> {
        return repo.values.filter { it.customerId == customerId }
    }

    override fun findAcademyInstructorInfoByAcademyId(academyId: Long): List<AcademyInstructor> {
        return repo.values.filter { it.academyId == academyId }
    }

    override fun findAcademyInstructorInfoAcademyIdAndCustomerId(
        customerId: Long,
        academyId: Long,
    ): AcademyInstructor? {
        return repo.values.find { (it.academyId == academyId && it.customerId == customerId) }
    }
}
