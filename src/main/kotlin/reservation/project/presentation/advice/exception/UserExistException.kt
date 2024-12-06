package reservation.project.presentation.advice.exception

import reservation.project.presentation.response.ResponseDto

class UserExistException(
    val responseDto: ResponseDto
): RuntimeException() {
}