package solidgate.user.balance.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import solidgate.user.balance.service.UserService

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping("/balance")
    fun setUsersBalance(@RequestBody userBalances: Map<Long, Double>): ResponseEntity<Any> {
        userService.setUsersBalance(userBalances)
        return ResponseEntity.status(HttpStatus.OK).body("User balances updated successfully")
    }
}