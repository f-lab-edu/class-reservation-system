package reservation.project.application.security.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtils {
    private val secretKey = "cSYEzypg3DVZJaxnrud7jt4TYBkVycRGSnuemLCTPaZME5QwNRrbm8DnXC539GZYhkaH6FKffrFqx2yNSh5Z7k8jgE36pLvaSmy8XBNufE2KDAws37H9V4Tt"
    private val expirationTime: Long = 3600000

    fun generateToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }


    fun extractUsername(token: String): String? {
        return try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
        } catch (e: Exception) {
            null
        }
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }


}