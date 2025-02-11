package reservation.project.domain.customer.service

import org.apache.catalina.connector.Response
import org.springframework.stereotype.Service
import reservation.project.domain.customer.entity.Customer
import reservation.project.domain.customer.repository.CustomerRepository
import reservation.project.infra.customer.CustomerJpaRepository
import reservation.project.presentation.advice.exception.ErrorException

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
){

    fun saveCustomer(customers: Customer): Customer {
        try{
            return customerRepository.save(customers)
                ?: throw ErrorException(Response.SC_CONFLICT, "Customer Save error")
        }catch (e: ErrorException){
            throw ErrorException(e.statusCode, e.errorMessage)
        }catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Academy Save Error")
        }
    }

    fun findCustomerInfo(uid: String): Customer? {
        try{
            return customerRepository.getByUid(uid)
                ?: throw ErrorException(Response.SC_NOT_FOUND, "Customer Not Found")
        }catch (e: ErrorException){
            throw ErrorException(e.statusCode, e.errorMessage)
        }catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Academy Save Error")
        }
    }

}