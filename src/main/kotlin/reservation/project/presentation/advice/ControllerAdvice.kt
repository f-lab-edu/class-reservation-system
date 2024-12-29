package reservation.project.presentation.advice

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reservation.project.presentation.advice.exception.ErrorException
import reservation.project.presentation.response.ResponseDataDto
import reservation.project.presentation.response.ResponseDto

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ResponseDto<String>> {
        return ResponseEntity.status(500).body(ResponseDto(500, "Server Error : " +e.message))
    }

    @ExceptionHandler(ErrorException::class)
    fun errorException(e: ErrorException): ResponseEntity<*> {
        return when (val dto = e.responseDto) {
            is ResponseDto<*> -> ResponseEntity.status(dto.code).body(dto)
            is ResponseDataDto<*> -> ResponseEntity.status(dto.code).body(dto)
            else -> ResponseEntity.status(500).body("Unknown error")
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ResponseDto<Map<String, String?>>> {
        val errors = e.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
        return ResponseEntity.badRequest().body(ResponseDto(HttpServletResponse.SC_BAD_REQUEST, errors))

    }

}