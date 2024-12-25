package reservation.project.infra.apply

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.reservation.entity.Apply
import java.util.Optional

@Repository
interface JpaApplyRepository: JpaRepository<Apply, Long> {
    fun findByApplyId(applyId: Long): Optional<Apply>
    fun findByClassId(classId: Long): List<Apply>
    fun findByUserId(userId: Long): List<Apply>
    fun save(apply: Apply): Optional<Apply>
}