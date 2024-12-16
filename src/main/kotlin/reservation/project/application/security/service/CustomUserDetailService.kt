package reservation.project.application.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import reservation.project.infra.user.JpaUserRepository

@Component
class CustomUserDetailService(
  private val userRepository: JpaUserRepository
) : UserDetailsService{

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username).orElse(null)
        return CustomUserDetails(user)
    }
}