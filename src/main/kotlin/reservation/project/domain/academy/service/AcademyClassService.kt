package reservation.project.domain.academy.service

import org.springframework.stereotype.Service
import reservation.project.domain.academy.entity.AcademyClass
import reservation.project.infra.academy.JpaAcademyClassRepository
import java.util.*

@Service
class AcademyClassService(
    private val jpaAcademyClassRepository: JpaAcademyClassRepository,
)  {
     fun findByAcademyId(academyId: Long): List<AcademyClass> {
        return jpaAcademyClassRepository.findByAcademyId(academyId)
    }

    fun findByAcademyClassId(academyClassId: Long): Optional<AcademyClass> {
        return jpaAcademyClassRepository.findByAcademyClassId(academyClassId)
    }

    fun findByCustomerId(CustomerId: Long): List<AcademyClass> {
        return jpaAcademyClassRepository.findByCustomerId(CustomerId)
    }

    fun findByAdminId(adminId: Long): List<AcademyClass> {
        return jpaAcademyClassRepository.findByAdminId(adminId)
    }

    fun findByAcademyIdAndAdminId(academyId: Long, adminId: Long): Optional<AcademyClass> {
        return jpaAcademyClassRepository.findByAcademyIdAndAdminId(academyId, adminId)
    }


    fun save(academyClass: AcademyClass): Optional<AcademyClass> {
        return jpaAcademyClassRepository.save(academyClass)
    }

    fun update(academyClass: AcademyClass): Optional<AcademyClass> {
        return jpaAcademyClassRepository.save(academyClass)
    }
}