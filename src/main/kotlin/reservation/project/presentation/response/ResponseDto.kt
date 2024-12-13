package reservation.project.presentation.response

data class ResponseDto<T>(
    val code: Int,
    val message: T
)
