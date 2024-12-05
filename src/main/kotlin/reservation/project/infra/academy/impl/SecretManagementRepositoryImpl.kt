package reservation.project.infra.academy.impl

import org.springframework.stereotype.Component
import reservation.project.domain.academy.entity.SecretManagement
import reservation.project.domain.academy.repository.SecretManagementRepository
import reservation.project.infra.academy.JpaSecretManagementRepository
import java.util.*

@Component
class SecretManagementRepositoryImpl(
    private val secretManagementRepo: JpaSecretManagementRepository
) : SecretManagementRepository {
    override fun findBySecretKey(secretKey: Int): Optional<SecretManagement> {
        return secretManagementRepo.findByKeyId(secretKey)
    }

    override fun save(secret: SecretManagement): Optional<SecretManagement> {
        return secretManagementRepo.save(secret)
    }

    override fun update(secret: SecretManagement): Optional<SecretManagement> {
        return secretManagementRepo.save(secret)
    }

}