package reservation.project.infra.impl

import org.springframework.stereotype.Component
import reservation.project.domain.customer.entity.User
import reservation.project.domain.customer.repository.UserRepository
import reservation.project.infra.JpaUserRepository
import java.util.Optional

@Component
class JpaUserRepositoryImpl(
    private val jpaUserRepository: JpaUserRepository
) : UserRepository{

    override fun findByUsername(username: String?): Optional<User> {
        return jpaUserRepository.findByUserName(username.toString())
    }

    override fun save(user: User): User {
        return jpaUserRepository.save(user)
    }
}