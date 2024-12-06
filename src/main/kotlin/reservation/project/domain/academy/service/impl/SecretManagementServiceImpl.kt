package reservation.project.domain.academy.service.impl

import org.springframework.stereotype.Service
import reservation.project.domain.academy.entity.SecretManagement
import reservation.project.domain.academy.repository.SecretManagementRepository
import reservation.project.domain.academy.service.SecretManagementService
import java.util.*

@Service
class SecretManagementServiceImpl(
    private val secretManagementRepository: SecretManagementRepository

) : SecretManagementService {
    override fun findByKeyId(keyId: Int): Optional<SecretManagement> {
        return secretManagementRepository.findByKeyId(keyId)
    }

    override fun findBySecretKey(secretKey: String): Optional<SecretManagement> {
        return secretManagementRepository.findBySecretKey(secretKey)
    }

    override fun save(secretKey: SecretManagement): Optional<SecretManagement> {
       return secretManagementRepository.save(secretKey)
    }
}