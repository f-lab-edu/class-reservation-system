package reservation.project.domain.user.service.impl

import org.springframework.stereotype.Service
import reservation.project.domain.user.entity.Customer
import reservation.project.domain.user.repository.UserRepository
import reservation.project.domain.user.service.UserService
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    override fun findByUsername(username: String): Optional<Customer> {
        return userRepository.findByUsername(username)
    }

    override fun save(customer: Customer): Optional<Customer> {
        return userRepository.save(customer)
    }

}