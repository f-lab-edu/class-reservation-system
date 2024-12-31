package reservation.project.infra.cart

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.cart.entity.Cart
import java.util.Optional

@Repository
interface JpaCartRepository : JpaRepository<Cart, Long>{

    fun findByUserId(userId: Long): List<Cart>

    fun findByUserIdAndClassId(userId: Long, classId: Long): Optional<Cart>

}