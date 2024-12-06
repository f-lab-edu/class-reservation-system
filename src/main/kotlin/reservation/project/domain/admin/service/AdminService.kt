package reservation.project.domain.admin.service

import reservation.project.domain.admin.entity.Admin
import java.util.*

interface AdminService {

    fun findByAdminName(adminName: String): Optional<Admin>
    fun save(admin: Admin): Optional<Admin>
}