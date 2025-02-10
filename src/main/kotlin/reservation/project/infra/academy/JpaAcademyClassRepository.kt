package reservation.project.infra.academy

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.academy.entity.AcademyClass
import java.util.Optional

@Repository
interface JpaAcademyClassRepository : JpaRepository<AcademyClass, Long>{

//    fun findWithPessimisticLockById(id: Long): Optional<AcademyClass>

    fun findByAcademyId(academyId: Long): List<AcademyClass>

}
