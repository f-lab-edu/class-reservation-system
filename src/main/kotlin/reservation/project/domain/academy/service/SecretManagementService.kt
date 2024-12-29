package reservation.project.domain.academy.service

import org.springframework.stereotype.Service
import reservation.project.domain.academy.entity.SecretManagement
import reservation.project.infra.academy.JpaSecretManagementRepository
import java.util.*

@Service
class SecretManagementService(
    private val jpaSecretManagementRepository : JpaSecretManagementRepository,

    )  {
     fun findByKeyId(keyId: Long): Optional<SecretManagement> {
        return jpaSecretManagementRepository.findById(keyId)
    }

     fun save(secretKey: SecretManagement): Optional<SecretManagement> {
       return jpaSecretManagementRepository.save(secretKey)
    }
}