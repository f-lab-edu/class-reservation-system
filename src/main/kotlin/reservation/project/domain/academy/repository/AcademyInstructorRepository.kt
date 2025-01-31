package reservation.project.domain.academy.repository

import reservation.project.domain.academy.entity.AcademyInstructor
import java.util.*

interface AcademyInstructorRepository {

    fun saveInfo(academyInstructor: AcademyInstructor): AcademyInstructor?

    fun findAcademyInstructorInfoById(id: Long): Optional<AcademyInstructor>

    fun findAcademyInstructorInfoByCustomerId(customerId: Long): List<AcademyInstructor>

    fun findAcademyInstructorInfoByAcademyId(academyId: Long): List<AcademyInstructor>

    fun findAcademyInstructorInfoAcademyIdAndCustomerId(customerId: Long, academyId: Long): AcademyInstructor?
}