package reservation.project.application.security.util

import org.springframework.stereotype.Component

@Component
class GenerateService {

    fun generateSecretCode(): String {
        val chars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..10)
            .map { chars.random() }
            .joinToString("")
    }
}