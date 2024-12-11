package reservation.project.domain.user.service

import org.springframework.stereotype.Service
import reservation.project.domain.user.entity.Customer
import reservation.project.domain.user.repository.UserRepository
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
){
     fun findByUsername(username: String): Optional<Customer> {
        return userRepository.findByUsername(username)
    }

     fun save(customer: Customer): Optional<Customer> {
        return userRepository.save(customer)
    }

}