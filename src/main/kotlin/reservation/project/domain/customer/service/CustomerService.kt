package reservation.project.domain.customer.service

import org.apache.catalina.connector.Response
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reservation.project.domain.customer.entity.Customer
import reservation.project.domain.customer.repository.CustomerRepository
import reservation.project.infra.customer.CustomerJpaRepository
import reservation.project.presentation.advice.exception.ErrorException

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
){

    @Transactional
    fun saveCustomer(customers: Customer): Customer {
        return customerRepository.save(customers)
            ?: throw ErrorException(Response.SC_CONFLICT, "Customer Save error")
    }

    fun findCustomerInfo(uid: String): Customer? {
        return customerRepository.getByUid(uid)
            ?: throw ErrorException(Response.SC_NOT_FOUND, "Customer Not Found")
    }

}
