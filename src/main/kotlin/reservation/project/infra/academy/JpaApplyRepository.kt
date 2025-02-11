package reservation.project.infra.academy

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.academy.entity.Apply
import java.util.*

@Repository
interface JpaApplyRepository: JpaRepository<Apply, Long> {

   override fun findById(id: Long): Optional<Apply>

    fun findByCustomerId(customerId: Long): List<Apply>

    fun findByAcademyClassIdAndCustomerId(academyClassId: Long, customerId: Long): Apply?

    fun findByAcademyClassId(academyClassId: Long): MutableList<Apply>

}
