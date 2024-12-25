package reservation.project.domain.admin.service

import org.springframework.stereotype.Service
import reservation.project.domain.admin.entity.Admin
import reservation.project.infra.admin.JpaAdminRepository
import java.util.*

@Service
class AdminService(
    private val jpaAdminRepository: JpaAdminRepository,
)  {
     fun findByAdminName(adminName: String): Optional<Admin> {
        return jpaAdminRepository.findByAdminName(adminName)
    }

    fun findByAdminId(adminId: Long): Optional<Admin> {
        return jpaAdminRepository.findByAdminId(adminId)
    }

     fun save(admin: Admin): Optional<Admin> {
        return jpaAdminRepository.save(admin)
    }
}