package reservation.project.domain.customer.repository

import reservation.project.domain.customer.entity.User
import java.util.Optional

interface UserRepository {
    fun findByUsername(username: String?): Optional<User>
    fun save(user: User): User
}