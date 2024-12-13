package reservation.project.domain.user.service

import org.springframework.stereotype.Service
import reservation.project.domain.user.entity.Customer
import reservation.project.infra.user.JpaUserRepository
import java.util.*

@Service
class UserService(
    private val jpaUserRepository: JpaUserRepository,
){
     fun findByUsername(username: String): Optional<Customer> {
        return jpaUserRepository.findByUsername(username)
    }

     fun save(customer: Customer): Optional<Customer> {
        return jpaUserRepository.save(customer)
    }

}