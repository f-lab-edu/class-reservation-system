package reservation.project.domain.user.service

import reservation.project.domain.user.entity.Customer
import java.util.*

interface UserService {

    fun findByUsername(username: String): Optional<Customer>
    fun save(customer: Customer): Optional<Customer>
}