package reservation.project.application.security.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@Component
class JwtUtils {
    private val secretKey = "cSYEzypg3DVZJaxnrud7jt4TYBkVycRGSnuemLCTPaZME5QwNRrbm8DnXC539GZYhkaH6FKffrFqx2yNSh5Z7k8jgE36pLvaSmy8XBNufE2KDAws37H9V4Tt"
    private val expirationTime: Long = 3600000

    fun generateToken(username: String): String {
        val now = Date()
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + expirationTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun accessToken(classId: Long, userId: Long): String {
        val claims = Jwts.claims().apply {
            put("classId", classId)
            put("userId", userId)
        }

        val now = Date()
        val validity = Date(now.time + expirationTime)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey.toByteArray())
            .compact()
    }

    fun extractUsername(token: String): String? {
        return try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
        } catch (e: Exception) {
            null
        }
    }

    fun extractTokenDetails(token: String): Map<String, LocalDateTime?> {
        val claims: Claims = Jwts.parser()
            .setSigningKey(secretKey.toByteArray())
            .parseClaimsJws(token)
            .body

        val issuedAt = claims.issuedAt?.toLocalDateTime() // 발행 시각
        val expiration = claims.expiration?.toLocalDateTime() // 만료 시각

        return mapOf(
            "issuedAt" to issuedAt,
            "expiration" to expiration
        )
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun Date.toLocalDateTime(): LocalDateTime {
        return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
    }


}