package reservation.project.domain.customer.service

import reservation.project.domain.customer.entity.User

interface AuthService {

    fun findByUsername(username: String):User
    fun save(user: User):User
}