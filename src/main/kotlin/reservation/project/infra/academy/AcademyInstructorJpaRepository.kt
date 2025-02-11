package reservation.project.infra.academy

import org.springframework.data.jpa.repository.JpaRepository
import reservation.project.domain.academy.entity.AcademyInstructor

interface AcademyInstructorJpaRepository : JpaRepository<AcademyInstructor, Long> {

    fun save(academyInstructor: AcademyInstructor): AcademyInstructor?

    fun findByAcademyId(academyId: Long): List<AcademyInstructor>

    fun findByCustomerId(customerId: Long): List<AcademyInstructor>

    fun findByAcademyIdAndCustomerId(academyId: Long, customerId: Long): AcademyInstructor?
}