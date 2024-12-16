package reservation.project.domain.token.service

import org.springframework.stereotype.Service
import reservation.project.domain.token.entity.Token
import reservation.project.infra.token.JpaTokenRepository
import reservation.project.presentation.token.dto.TokenQueueDto
import java.util.Optional

@Service
class TokenService(
    private val jpaTokenRepository: JpaTokenRepository
) {

    fun findByAcademyClassIdAndCustomerId(tokenQueueDto: TokenQueueDto): Optional<Token> {
        return jpaTokenRepository.findByAcademyClassIdAndCustomerId(tokenQueueDto.classId, tokenQueueDto.userId)
    }

    fun addQueue(token: Token): Optional<Token> {
        return jpaTokenRepository.save(token)
    }

    fun modifyQueue(token: Token): Optional<Token> {
        return jpaTokenRepository.save(token)
    }
}