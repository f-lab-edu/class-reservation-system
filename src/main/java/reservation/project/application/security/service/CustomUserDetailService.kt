package reservation.project.application.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import reservation.project.domain.customer.service.AuthService

@Service
class CustomUserDetailService(
    private val authService: AuthService
) : UserDetailsService{

    override fun loadUserByUsername(username: String): UserDetails {
        val user = authService.findByUsername(username)
        return CustomUserDetails(user)
    }
}