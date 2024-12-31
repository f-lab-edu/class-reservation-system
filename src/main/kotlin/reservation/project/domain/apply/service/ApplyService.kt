package reservation.project.domain.apply.service

import org.springframework.stereotype.Service
import reservation.project.domain.apply.entity.Apply
import reservation.project.infra.apply.JpaApplyRepository
import java.util.*

@Service
class ApplyService(
    private val jpaApplyRepository : JpaApplyRepository
) {

    fun findByApplyId(applyId: Long): Optional<Apply> {
        return jpaApplyRepository.findById(applyId)
    }

    fun findByClassId(classId: Long): List<Apply> {
        return jpaApplyRepository.findByClassId(classId)
    }

    fun findByUserId(userId: Long): List<Apply> {
        return jpaApplyRepository.findByUserId(userId)
    }

    fun save(apply: Apply): Apply {
        return jpaApplyRepository.save(apply)
    }

    fun update(apply: Apply): Apply {
        return jpaApplyRepository.save(apply)
    }
}