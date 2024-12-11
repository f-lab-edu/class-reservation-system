package reservation.project.domain.admin.repository

import reservation.project.domain.admin.entity.Admin
import java.util.Optional

interface AdminRepository {
    fun findByAdminName(adminName: String): Optional<Admin>
    fun save(admin: Admin): Optional<Admin>
}