package reservation.project.application.cart

import org.apache.catalina.connector.Response
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Component
import reservation.project.domain.academy.service.AcademyClassService
import reservation.project.domain.academy.status.ClassStatus
import reservation.project.domain.apply.service.ApplyService
import reservation.project.domain.cart.entity.Cart
import reservation.project.domain.cart.service.CartService
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.presentation.cart.dto.CartReqDto
import reservation.project.presentation.cart.dto.CartResDto
import reservation.project.presentation.response.ResponseDto

@Component
class CartUseCase(
    private val cartService: CartService,
    private val academyClassService: AcademyClassService,
    private val applyService: ApplyService
) {

    fun registerCart(req: CartReqDto) {
        // 카트 정보 유무 체크
        val cartResult = cartService.findByUserIdAndClassId(req.userId, req.classId)
        if (cartResult.isPresent) {
            throw ErrorException(Response.SC_CONFLICT, "this information is already being cart for")
        }

        // 수업 정보 유무 체크
        val classInfo = academyClassService.findByAcademyClassId(req.classId).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "Academy class information not found")
        }

        // 수업 상태 체크
        if (classInfo.status != ClassStatus.PROGRESS){
            throw ErrorException(Response.SC_CONFLICT, "This class is either closed or not open yet")
        }

        // 수업 신청인원 체크
        val numberOfApplicants = applyService.findByClassId(req.classId).size
        if (classInfo.isCapacityExceeded(numberOfApplicants)){
            throw ErrorException(Response.SC_CONFLICT, "Class capacity exceeded")
        }

        val registerCartInfo = Cart(0, req.classId, req.userId, classInfo.status!!)

        try {
            cartService.save(registerCartInfo)
        }catch (e: DataAccessException){
            throw ErrorException(Response.SC_BAD_REQUEST, "Database error: ${e.message}")
        }catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Unexpected error: ${e.message}")
        }

    }

    fun getClassInfoByUserId(userId: Long): List<CartResDto>{
        var cartInfo: List<Cart> = try{
           cartService.findByUserId(userId)
        }catch (e: Exception) {
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Unexpected error: ${e.message}")
        }

        val cartList = cartInfo.takeIf { it.isNotEmpty() } ?: throw ErrorException(Response.SC_NOT_FOUND, "No cart information found for userId: $userId")

        val classIds = cartList.map { it.classId }

        val classInfoList = academyClassService.findByAcademyClassIds(classIds)

        val classInfo= ArrayList<CartResDto>()
        for(info in cartList) {
            val result = classInfoList.find { it.classId == info.classId }
                ?: throw ErrorException(Response.SC_NOT_FOUND, "Academy class information not found for classId: ${info.classId}")

            val resDataInfo = CartResDto(result.className, result.capacity, result.classRegistStartDate, result.classRegistDeadlineDate, result.classStartTime, result.classCloseTime, result.classTuition, result.classInstructor, result.status)
            classInfo.add(resDataInfo)
        }

        return classInfo
    }

    fun getClassInfoByCartId(cartId: Long): CartResDto {
       val cartInfo =  cartService.findById(cartId).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "not found cart info")
        }

        val result = academyClassService.findByAcademyClassId(cartInfo.classId).orElseThrow {
            ErrorException(Response.SC_NOT_FOUND, "Academy class information not found")
        }

        val resDataInfo = CartResDto(result.className, result.capacity, result.classRegistStartDate, result.classRegistDeadlineDate, result.classStartTime, result.classCloseTime, result.classTuition, result.classInstructor, result.status)
        return resDataInfo
    }

    fun deleteByCartId(cartId: Long) {
        try{
            cartService.delete(cartId)
        }catch (e: DataAccessException){
            throw ErrorException(Response.SC_BAD_REQUEST, "Database error: ${e.message}")
        }catch (e: Exception){
            throw ErrorException(Response.SC_INTERNAL_SERVER_ERROR, "Unexpected error: ${e.message}")
        }
    }

}