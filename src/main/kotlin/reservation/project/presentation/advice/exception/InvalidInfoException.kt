package reservation.project.presentation.advice.exception

import reservation.project.presentation.response.ResponseDto

class InvalidInfoException (
    val responseDto: ResponseDto
): RuntimeException() {
}