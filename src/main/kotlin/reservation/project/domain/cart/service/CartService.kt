package reservation.project.domain.cart.service

import org.apache.catalina.connector.Response
import org.springframework.stereotype.Service
import reservation.project.domain.cart.entity.Cart
import reservation.project.infra.cart.JpaCartRepository
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.presentation.response.ResponseDto
import java.util.Optional

@Service
class CartService(
    private val jpaCartRepository: JpaCartRepository
) {

    fun save(cart: Cart): Cart {
        return jpaCartRepository.save(cart)
    }

    fun findByUserId(userId: Long): List<Cart> {
        return jpaCartRepository.findByUserId(userId)
    }

    fun findByUserIdAndClassId(userId: Long, classId: Long): Optional<Cart> {
        return jpaCartRepository.findByUserIdAndClassId(userId, classId)
    }

    fun findById(cartId: Long): Optional<Cart> {
        return jpaCartRepository.findById(cartId)
    }

    fun delete(cartId: Long) {
        try {
            jpaCartRepository.deleteById(cartId)
        }catch (e: Exception) {
            throw ErrorException(ResponseDto(Response.SC_INTERNAL_SERVER_ERROR, "Cart Delete Error"))
        }
    }
}