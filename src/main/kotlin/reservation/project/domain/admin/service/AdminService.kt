package reservation.project.domain.admin.service

import org.springframework.stereotype.Service
import reservation.project.domain.admin.entity.Admin
import reservation.project.domain.admin.repository.AdminRepository
import java.util.*

@Service
class AdminService(
    private val adminRepository: AdminRepository
)  {
     fun findByAdminName(adminName: String): Optional<Admin> {
        return adminRepository.findByAdminName(adminName)
    }

     fun save(admin: Admin): Optional<Admin> {
        return adminRepository.save(admin)
    }
}