package reservation.project.infra.customer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.customer.entity.Customer

@Repository
interface CustomerJpaRepository : JpaRepository<Customer, Long>{
    fun getByUid(uid: String): Customer?
    fun save(customers: Customer): Customer?
}