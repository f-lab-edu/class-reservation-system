package reservation.project.domain.reservation.service

import org.springframework.stereotype.Service
import reservation.project.domain.reservation.entity.Apply
import reservation.project.infra.apply.JpaApplyRepository
import java.util.*

@Service
class ApplyService(
    private val jpaApplyRepository : JpaApplyRepository
) {

    fun findByApplyId(applyId: Long): Optional<Apply> {
        return jpaApplyRepository.findByApplyId(applyId)
    }

    fun findByClassId(classId: Long): List<Apply> {
        return jpaApplyRepository.findByClassId(classId)
    }

    fun findByUserId(userId: Long): List<Apply> {
        return jpaApplyRepository.findByUserId(userId)
    }

    fun save(apply: Apply): Optional<Apply> {
        return jpaApplyRepository.save(apply)
    }

    fun update(apply: Apply): Optional<Apply> {
        return jpaApplyRepository.save(apply)
    }
}