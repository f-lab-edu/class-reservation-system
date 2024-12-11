package reservation.project.domain.academy.service

import org.springframework.stereotype.Service
import reservation.project.domain.academy.entity.SecretManagement
import reservation.project.domain.academy.repository.SecretManagementRepository
import java.util.*

@Service
class SecretManagementService(
    private val secretManagementRepository: SecretManagementRepository

)  {
     fun findByKeyId(keyId: Int): Optional<SecretManagement> {
        return secretManagementRepository.findBySecretKey(keyId)
    }

     fun save(secretKey: SecretManagement): Optional<SecretManagement> {
       return secretManagementRepository.save(secretKey)
    }
}