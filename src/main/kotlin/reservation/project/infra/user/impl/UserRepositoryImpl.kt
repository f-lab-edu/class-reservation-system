package reservation.project.infra.user.impl

import org.springframework.stereotype.Component
import reservation.project.domain.user.entity.Customer
import reservation.project.domain.user.repository.UserRepository
import reservation.project.infra.user.JpaUserRepository
import java.util.Optional

@Component
class UserRepositoryImpl(
    private val jpaUserRepository: JpaUserRepository
) : UserRepository {

    override fun findByUsername(username: String): Optional<Customer> {
        return jpaUserRepository.findByUsername(username.toString())
    }

    override fun save(customer: Customer): Optional<Customer> {
        return jpaUserRepository.save(customer)
    }
}