package reservation.project.domain.academy.service

import org.apache.catalina.connector.Response
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reservation.project.domain.academy.entity.Apply
import reservation.project.infra.academy.JpaApplyRepository
import reservation.project.presentation.advice.exception.ErrorException

@Service
class ApplyService(
    private val jpaApplyRepository : JpaApplyRepository
) {

    @Transactional
    fun saveInfo(apply: Apply): Apply {
        return jpaApplyRepository.save(apply)
    }

    @Transactional
    fun findById(id: Long): Apply {
        return jpaApplyRepository.findById(id).orElseThrow { ErrorException(Response.SC_NOT_FOUND, "Apply Not Found") }
    }

    @Transactional
    fun findByCustomerId(customerId: Long): List<Apply> {
        val applies = jpaApplyRepository.findByCustomerId(customerId)
        if (applies.isEmpty()) {
            throw ErrorException(Response.SC_NOT_FOUND, "User is not applied")
        }
        return applies
    }

    @Transactional
    fun findByAcademyClassIdAndCustomerId(academyClassId: Long, customerId: Long): Apply? {
        return jpaApplyRepository.findByAcademyClassIdAndCustomerId(academyClassId, customerId)
    }

    @Transactional
    fun findByAcademyClassId(academyClassId: Long): MutableList<Apply> {
        val applies = jpaApplyRepository.findByAcademyClassId(academyClassId)
        if(applies.isEmpty()){
            throw ErrorException(Response.SC_NOT_FOUND, "Applied not exists")
        }

        return applies
    }
}
