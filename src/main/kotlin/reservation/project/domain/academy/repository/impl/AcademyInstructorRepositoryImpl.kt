package reservation.project.domain.academy.repository.impl


import org.springframework.stereotype.Component
import reservation.project.domain.academy.entity.AcademyInstructor
import reservation.project.domain.academy.repository.AcademyInstructorRepository
import reservation.project.infra.academy.AcademyInstructorJpaRepository
import java.util.Optional

@Component
class AcademyInstructorRepositoryImpl(
    private val academyInstructorJpaRepository: AcademyInstructorJpaRepository
) : AcademyInstructorRepository {

    override fun saveInfo(academyInstructor: AcademyInstructor): AcademyInstructor? {
        return academyInstructorJpaRepository.save(academyInstructor)
    }

    override fun findAcademyInstructorInfoById(id: Long): Optional<AcademyInstructor> {
        return academyInstructorJpaRepository.findById(id)
    }

    override fun findAcademyInstructorInfoByCustomerId(customerId: Long): List<AcademyInstructor> {
        return academyInstructorJpaRepository.findByCustomerId(customerId)
    }

    override fun findAcademyInstructorInfoByAcademyId(academyId: Long): List<AcademyInstructor> {
        return academyInstructorJpaRepository.findByAcademyId(academyId)
    }

    override fun findAcademyInstructorInfoAcademyIdAndCustomerId(customerId: Long, academyId: Long): AcademyInstructor? {
        return academyInstructorJpaRepository.findByAcademyIdAndCustomerId(academyId, customerId)
    }
}