package reservation.project.domain.academy.repository

import reservation.project.domain.academy.entity.SecretManagement
import java.util.Optional

interface SecretManagementRepository {
    fun findByKeyId(keyId: Int): Optional<SecretManagement>
    fun findBySecretKey(secretKey: String): Optional<SecretManagement>
    fun save(secret: SecretManagement): Optional<SecretManagement>
    fun update(secret: SecretManagement): Optional<SecretManagement>
}