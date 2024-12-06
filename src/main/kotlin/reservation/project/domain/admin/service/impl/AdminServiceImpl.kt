package reservation.project.domain.admin.service.impl

import org.springframework.stereotype.Service
import reservation.project.domain.admin.entity.Admin
import reservation.project.domain.admin.repository.AdminRepository
import reservation.project.domain.admin.service.AdminService
import java.util.*

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository
) : AdminService {
    override fun findByAdminName(adminName: String): Optional<Admin> {
        return adminRepository.findByAdminName(adminName)
    }

    override fun save(admin: Admin): Optional<Admin> {
        return adminRepository.save(admin)
    }
}