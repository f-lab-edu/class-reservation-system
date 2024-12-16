package reservation.project.infra.token

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.token.entity.Token
import java.util.Optional

@Repository
interface JpaTokenRepository: JpaRepository<Token, Long> {

    fun findByAcademyClassIdAndCustomerId(academyClassId: Long, customerId: Long): Optional<Token>
    fun save(token: Token): Optional<Token>
}