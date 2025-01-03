package reservation.project.presentation.cart.dto

import reservation.project.domain.academy.status.ClassStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime

data class CartResDto(
    val className: String,
    val capacity: Int,
    var classRegistStartDate: LocalDateTime,
    var classRegistDeadlineDate: LocalDateTime,
    var classStartTime: LocalTime,
    var classCloseTime: LocalTime,
    var classTuition: BigDecimal,
    var classInstructor: String,
    var status: ClassStatus? = null
)
