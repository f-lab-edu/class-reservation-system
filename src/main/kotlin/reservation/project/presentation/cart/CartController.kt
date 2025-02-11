package reservation.project.presentation.cart


import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.application.cart.CartUseCase

@RestController
@RequestMapping("/cart")
class CartController(
    private val cartUseCase: CartUseCase
) {

//    @PostMapping
//    fun addCart(@RequestBody @Valid req: CartReqDto): ResponseDto<String> {
//        cartUseCase.registerCart(req)
//        return ResponseDto(200, "Success")
//    }
//
//    @GetMapping("/user/{userId}")
//    fun getCartByUserId(@PathVariable("userId") userId: Long): ResponseDataDto<List<CartResDto>> {
//        return ResponseDataDto(200, "Success", cartUseCase.getClassInfoByUserId(userId))
//    }
//
//    @GetMapping("/{cartId}")
//    fun getCartByCartId(@PathVariable("cartId") cartId: Long): ResponseDataDto<CartResDto> {
//        return ResponseDataDto(200, "Success", cartUseCase.getClassInfoByCartId(cartId))
//    }
//
//    @DeleteMapping("/{cartId}")
//    fun deleteCartByCartId(@PathVariable("cartId") cartId: Long): ResponseDto<String> {
//        cartUseCase.deleteByCartId(cartId)
//        return ResponseDto(200, "Success")
//    }

}
