package reservation.project.infra.apply

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.apply.entity.Apply

@Repository
interface JpaApplyRepository: JpaRepository<Apply, Long> {

    fun findByClassId(classId: Long): List<Apply>
    fun findByUserId(userId: Long): List<Apply>

}