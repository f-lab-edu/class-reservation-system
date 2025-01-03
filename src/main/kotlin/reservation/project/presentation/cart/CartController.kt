package reservation.project.presentation.cart

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.application.cart.CartUseCase
import reservation.project.presentation.cart.dto.CartReqDto
import reservation.project.presentation.cart.dto.CartResDto
import reservation.project.presentation.response.ResponseDataDto
import reservation.project.presentation.response.ResponseDto

@RestController
@RequestMapping("/cart")
class CartController(
    private val cartUseCase: CartUseCase
) {

    @PostMapping
    fun addCart(@RequestBody @Valid req: CartReqDto): ResponseDto<String> {
        cartUseCase.registerCart(req)
        return ResponseDto(200, "Success")
    }

    @GetMapping("/user/{userId}")
    fun getCartByUserId(@PathVariable("userId") userId: Long): ResponseDataDto<List<CartResDto>> {
        return ResponseDataDto(200, "Success", cartUseCase.getClassInfoByUserId(userId))
    }

    @GetMapping("/{cartId}")
    fun getCartByCartId(@PathVariable("cartId") cartId: Long): ResponseDataDto<CartResDto> {
        return ResponseDataDto(200, "Success", cartUseCase.getClassInfoByCartId(cartId))
    }

    @DeleteMapping("/{cartId}")
    fun deleteCartByCartId(@PathVariable("cartId") cartId: Long): ResponseDto<String> {
        cartUseCase.deleteByCartId(cartId)
        return ResponseDto(200, "Success")
    }

}