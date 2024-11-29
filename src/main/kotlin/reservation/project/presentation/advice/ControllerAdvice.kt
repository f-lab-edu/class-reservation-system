package reservation.project.presentation.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reservation.project.presentation.advice.exception.InvalidInfoException
import reservation.project.presentation.advice.exception.UserExistException
import reservation.project.presentation.response.ResponseDto

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ResponseDto> {
        return ResponseEntity.status(500).body(ResponseDto(500, "Server Error : " +e.message, null))
    }

    @ExceptionHandler(UserExistException::class)
    fun userExistException(e: UserExistException): ResponseEntity<ResponseDto> {
        return ResponseEntity.status(500).body(e.responseDto)
    }

    @ExceptionHandler(InvalidInfoException::class)
    fun invalidInfoException(e: InvalidInfoException): ResponseEntity<ResponseDto> {
        return ResponseEntity.status(500).body(e.responseDto)
    }
}