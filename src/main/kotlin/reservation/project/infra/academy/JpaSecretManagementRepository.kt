package reservation.project.infra.academy

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.academy.entity.SecretManagement
import java.util.Optional

@Repository
interface JpaSecretManagementRepository : JpaRepository<SecretManagement, Long> {
    override fun findById(keyId: Long): Optional<SecretManagement>
    fun save(secretKey: SecretManagement): Optional<SecretManagement>
}