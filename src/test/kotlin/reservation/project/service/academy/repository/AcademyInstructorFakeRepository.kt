package reservation.project.service.academy.repository


import org.slf4j.LoggerFactory
import reservation.project.domain.academy.entity.AcademyInstructor
import reservation.project.domain.academy.repository.AcademyInstructorRepository
import reservation.project.presentation.advice.exception.ErrorException
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class AcademyInstructorFakeRepository : AcademyInstructorRepository {
    private val repo = ConcurrentHashMap<Long, AcademyInstructor>()
    private var idCounter: Long = 1L
    private val logger = LoggerFactory.getLogger(AcademyInstructorFakeRepository::class.java)

    override fun saveInfo(academyInstructor: AcademyInstructor): AcademyInstructor? {
        val savedEntity = academyInstructor.copy(id = idCounter++)
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