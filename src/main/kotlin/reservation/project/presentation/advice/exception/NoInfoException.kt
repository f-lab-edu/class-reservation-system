package reservation.project.presentation.advice.exception

import reservation.project.presentation.response.ResponseDto

class NoInfoException(
    val responseDto: ResponseDto
): RuntimeException() {
}