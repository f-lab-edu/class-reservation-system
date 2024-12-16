package reservation.project.presentation.token

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reservation.project.presentation.response.ResponseDataDto
import reservation.project.presentation.response.ResponseDto
import reservation.project.presentation.token.dto.TokenQueueReqDto

@RestController
@RequestMapping("/token")
class TokenController {

    @PostMapping("/queue")
    fun register(@Valid @RequestBody tokenQueueDto: TokenQueueReqDto): ResponseEntity<ResponseDataDto<String>>{
        return ResponseEntity.ok(ResponseDataDto(200, "Success", "tokenJwt"))
    }

    @PostMapping("/get")
    fun getInfo(@PathVariable @NotEmpty validToken: String): ResponseEntity<ResponseDto<String>>{
        return ResponseEntity.ok(ResponseDto(200, "Success"))
    }

    @PutMapping("/status")
    fun updateStatus(@PathVariable @NotEmpty validToken: String): ResponseEntity<ResponseDto<String>>{
        return ResponseEntity.ok(ResponseDto(200, "Success"))
    }
}