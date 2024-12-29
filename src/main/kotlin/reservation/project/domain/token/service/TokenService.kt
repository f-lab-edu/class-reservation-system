package reservation.project.domain.token.service

import org.springframework.stereotype.Service
import reservation.project.domain.token.entity.Token
import reservation.project.infra.token.JpaTokenRepository
import java.util.Optional

@Service
class TokenService(
    private val jpaTokenRepository: JpaTokenRepository
) {

    fun findByAcademyClassIdAndCustomerId(academyClassId: Long, customerId: Long): Optional<Token> {
        return jpaTokenRepository.findByAcademyClassIdAndCustomerId(academyClassId, customerId)
    }

    fun findByAcademyClassId(academyClassId: Long): Optional<Token> {
        return jpaTokenRepository.findByAcademyClassId(academyClassId)
    }

    fun findByCustomerId(customerId: Long): Optional<Token> {
        return jpaTokenRepository.findByCustomerId(customerId)
    }

    fun addQueue(token: Token): Optional<Token> {
        return jpaTokenRepository.save(token)
    }

    fun modifyQueue(token: Token): Optional<Token> {
        return jpaTokenRepository.save(token)
    }
}