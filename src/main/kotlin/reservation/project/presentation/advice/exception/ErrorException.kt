package reservation.project.presentation.advice.exception


class ErrorException(
    val statusCode: Int,
    val errorMessage: String
): RuntimeException()