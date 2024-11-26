package reservation.project.domain.customer.service.impl

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import reservation.project.domain.customer.entity.User
import reservation.project.domain.customer.repository.UserRepository
import reservation.project.domain.customer.service.AuthService

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
): AuthService {
    override fun findByUsername(username: String): User {
        return userRepository.findByUsername(username)
            .orElse(null)
    }

    override fun save(user: User): User {
        return userRepository.save(user)
    }
}