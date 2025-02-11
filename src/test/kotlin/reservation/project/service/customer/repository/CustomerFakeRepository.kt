package reservation.project.service.customer.repository

import reservation.project.domain.customer.entity.Customer
import reservation.project.domain.customer.repository.CustomerRepository
import java.util.concurrent.ConcurrentHashMap

class CustomerFakeRepository: CustomerRepository {

    private val repo = ConcurrentHashMap<Long, Customer>()
    private var idCounter: Long = 1L

    override fun getByUid(uid: String): Customer? {
        for (customer in repo.values) {
            if(customer.uid == uid) {
                return customer
            }
        }
        return null
    }

    override fun save(customers: Customer): Customer? {
        val savedEntity =customers.copy(id = idCounter++)
        repo[(savedEntity).id] = savedEntity
        return savedEntity
    }
}