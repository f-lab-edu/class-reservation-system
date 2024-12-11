package reservation.project.infra.admin.impl

import org.springframework.stereotype.Component
import reservation.project.domain.admin.entity.Admin
import reservation.project.domain.admin.repository.AdminRepository
import java.util.Optional

@Component
class AdminRepositoryImpl(
    private val jpaAdminRepository: AdminRepository
) : AdminRepository {
    override fun findByAdminName(adminName: String): Optional<Admin> {
        return jpaAdminRepository.findByAdminName(adminName)
    }

    override fun save(admin: Admin): Optional<Admin> {
        return jpaAdminRepository.save(admin)
    }


}