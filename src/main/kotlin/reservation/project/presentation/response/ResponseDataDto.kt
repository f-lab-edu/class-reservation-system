package reservation.project.presentation.response

data class ResponseDataDto<T>(
    val code: Int,
    val message: String,
    val data: T
)

