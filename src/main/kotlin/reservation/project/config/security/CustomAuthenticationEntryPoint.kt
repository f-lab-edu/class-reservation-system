package reservation.project.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import reservation.project.presentation.response.ResponseDto

class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val mapper = ObjectMapper()

        val errorRes = ResponseDto<String>(401, "인증이 실패하였습니다.")

        response?.status = 401
        response?.contentType = "application/json"
        response?.characterEncoding = "utf-8"
        response?.writer?.write(mapper.writeValueAsString(errorRes))
    }
}