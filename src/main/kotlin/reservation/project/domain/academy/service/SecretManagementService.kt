package reservation.project.domain.academy.service

import reservation.project.domain.academy.entity.SecretManagement
import java.util.*

interface SecretManagementService {
    fun findByKeyId(keyId: Int): Optional<SecretManagement>
    fun findBySecretKey(secretKey: String): Optional<SecretManagement>
    fun save(secretKey: SecretManagement): Optional<SecretManagement>
}