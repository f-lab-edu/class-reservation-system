package reservation.project.domain.user.repository

import reservation.project.domain.user.entity.Customer
import java.util.Optional

interface UserRepository {
    fun findByUsername(username: String): Optional<Customer>
    fun save(customer: Customer): Optional<Customer>
}