package reservation.project.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reservation.project.domain.customer.service.CustomersDetailsService
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class JwtTokenProvider(
    private val userDetailsService: CustomersDetailsService
) {
    @Value("\${spring.jwt.secretKey}")
    private lateinit var secretKey: String
    private val tokenValidMillisecond:Long = 1000L * 60 * 60

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray(StandardCharsets.UTF_8))
    }

    fun createToken( userUid: String, roles: List<String>): String {
        val claims: Claims = Jwts.claims().setSubject(userUid)
        claims.put("roles", roles)
        val now = Date()

        val token:String = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenValidMillisecond))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()

        return token
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(this.getUserName(token))
        return UsernamePasswordAuthenticationToken(userDetails, "",userDetails.authorities)
    }

    fun getUserName(token:String): String {
        val info = Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).body.subject
        return info
    }

    fun resolveToken(req: HttpServletRequest): String {
        return req.getHeader("X-AUTH-TOKEN")
    }

    fun validateToken(token: String): Boolean {
        try{
            val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token)
            return !claims.body.expiration.before(Date())
        }catch (e: Exception){
            return false
        }
    }
}