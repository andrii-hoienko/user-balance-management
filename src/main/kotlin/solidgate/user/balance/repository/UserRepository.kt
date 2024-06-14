package solidgate.user.balance.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import solidgate.user.balance.model.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findAllByIdIn(ids: Set<Long?>?): Set<User>
}