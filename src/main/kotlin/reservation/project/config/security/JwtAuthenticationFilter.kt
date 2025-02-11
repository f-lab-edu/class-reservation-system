package reservation.project.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = jwtTokenProvider.resolveToken(request)
            if (jwtTokenProvider.validateToken(token)) {
                val authentication: Authentication = jwtTokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            jwtExceptionHandler(response)
        }

        filterChain.doFilter(request, response)
    }

    private fun jwtExceptionHandler(res: HttpServletResponse){
        res.status = 403
        res.contentType = "application/json"
    }
}