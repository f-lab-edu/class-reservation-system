package reservation.project.domain.customer.repository

import reservation.project.domain.customer.entity.Customer

interface CustomerRepository {

    fun getByUid(uid: String): Customer?
    fun save(customers: Customer): Customer?
}