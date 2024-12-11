package reservation.project.presentation.advice.exception


class ErrorException(
    val responseDto: Any
): RuntimeException() {

}