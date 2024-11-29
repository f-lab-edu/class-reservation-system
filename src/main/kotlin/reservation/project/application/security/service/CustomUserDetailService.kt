package reservation.project.application.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import reservation.project.domain.user.repository.UserRepository

@Component
class CustomUserDetailService(
  private val userRepository: UserRepository
) : UserDetailsService{

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username).orElse(null)
        return CustomUserDetails(user)
    }
}