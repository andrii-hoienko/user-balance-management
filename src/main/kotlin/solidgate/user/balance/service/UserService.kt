package solidgate.user.balance.service

interface UserService {
    fun setUsersBalance(userBalances: Map<Long, Double>)
}