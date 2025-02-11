package reservation.project.domain.customer.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import reservation.project.infra.customer.CustomerJpaRepository
import reservation.project.presentation.advice.exception.ErrorException

@Service
class CustomersDetailsService(
    private val customerJpaRepository: CustomerJpaRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return customerJpaRepository.getByUid(username!!) ?: throw ErrorException(404, "Customer Not Found")
    }
}