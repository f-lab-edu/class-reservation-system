package reservation.project.application.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import reservation.project.application.security.service.CustomUserDetailService
import reservation.project.application.security.util.JwtUtils

@Component
@Slf4j
class JwtAuthenticationFilter(
    private val jwtUtils: JwtUtils,
    private val customUserDetailService: UserDetailsService
) : OncePerRequestFilter(){
    private val log: Logger = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader.isNullOrBlank() || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authorizationHeader.substring(7)
        if (jwtUtils.isTokenValid(token)){
            val username = jwtUtils.extractUsername(token)

            if(!username.isNullOrEmpty() && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = customUserDetailService.loadUserByUsername(username.toString())
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }


}