package reservation.project.infra.admin

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import reservation.project.domain.admin.entity.Admin
import java.util.Optional

@Repository
interface JpaAdminRepository : JpaRepository<Admin, Long>{
    fun findByAdminName(adminName:String): Optional<Admin>
    fun save(admin:Admin): Optional<Admin>
}