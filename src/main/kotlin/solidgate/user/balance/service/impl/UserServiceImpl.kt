package solidgate.user.balance.service.impl

import org.springframework.stereotype.Service
import solidgate.user.balance.exception.UserNotFoundException
import solidgate.user.balance.model.User
import solidgate.user.balance.repository.UserRepository
import solidgate.user.balance.service.UserService
import java.util.stream.Collectors.toSet

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun setUsersBalance(userBalances: Map<Long, Double>) {
        val userIds = userBalances.keys.toSet()
        val users = userRepository.findAllByIdIn(userIds)
        validateIds(userIds, users)

        users.forEach { user ->
            val balanceValue = getBalance(userBalances, user)
            user.balance += balanceValue
        }

        userRepository.saveAll(users)
    }

    private fun validateIds(userIds: Set<Long>, users: Set<User>) {
        if (userIds.size != (users.size)) {
            val actualUserIds = users.stream().map(User::id).collect(toSet())
            val missingIds = userIds.subtract(actualUserIds)
            throw UserNotFoundException("Users with ids: $missingIds not exists")
        }
    }

    private fun getBalance(userBalances: Map<Long, Double>, user: User): Double {
        return userBalances[user.id]
                ?: throw IllegalStateException("No balance found for user with id: ${user.id}")
    }
}