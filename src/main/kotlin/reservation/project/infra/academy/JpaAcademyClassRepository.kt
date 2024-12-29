package reservation.project.infra.academy

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.academy.entity.AcademyClass
import java.util.Optional

@Repository
interface JpaAcademyClassRepository : JpaRepository<AcademyClass, Long>{

    fun findByAcademyId(academyId: Long): List<AcademyClass>
    fun findByAcademyClassId(academyClassId: Long): Optional<AcademyClass>
    fun findByCustomerId(CustomerId: Long): List<AcademyClass>
    fun findByAdminId(adminId: Long): List<AcademyClass>
    fun findByAcademyIdAndAdminId(academyId: Long, adminId: Long): Optional<AcademyClass>
    fun save(academyClass: AcademyClass): Optional<AcademyClass>
}