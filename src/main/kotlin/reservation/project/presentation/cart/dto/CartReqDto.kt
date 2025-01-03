package reservation.project.presentation.cart.dto

import jakarta.validation.constraints.NotNull

data class CartReqDto(

    @field:NotNull(message = "유저정보를 찾을 수 없습니다. : NULL")
    val userId: Long,

    @field:NotNull(message = "강의 정보를 찾을 수 없습니다. : NULL")
    val classId: Long
)
