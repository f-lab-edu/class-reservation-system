package reservation.project.domain.customer.repository.impl

import org.springframework.stereotype.Component
import reservation.project.domain.customer.entity.Customer
import reservation.project.domain.customer.repository.CustomerRepository
import reservation.project.infra.customer.CustomerJpaRepository

@Component
class CustomerRepositoryImpl(
    private val customerJpaRepository: CustomerJpaRepository
): CustomerRepository {
    override fun getByUid(uid: String): Customer? {
        return customerJpaRepository.getByUid(uid)
    }

    override fun save(customers: Customer): Customer? {
        return customerJpaRepository.save(customers)
    }
}