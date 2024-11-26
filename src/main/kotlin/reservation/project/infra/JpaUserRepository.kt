package reservation.project.infra

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.customer.entity.User
import java.util.*

@Repository
interface JpaUserRepository :  JpaRepository<User, Long>{
    fun findByUserName(userName:String): Optional<User>
}