package reservation.project.infra.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.user.entity.Customer
import java.util.*

@Repository
interface JpaUserRepository : JpaRepository<Customer, Long>{
    fun findByUsername(username:String): Optional<Customer>
    fun save(customer: Customer): Optional<Customer>
}