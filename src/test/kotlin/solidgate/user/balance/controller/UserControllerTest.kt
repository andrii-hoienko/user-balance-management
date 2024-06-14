package solidgate.user.balance.controller

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit.jupiter.SpringExtension
import solidgate.user.balance.dto.ErrorResponseDto
import solidgate.user.balance.repository.UserRepository
import kotlin.test.assertEquals

private const val SUCCESS_MESSAGE = "User balances updated successfully"
private const val USER_ID_NOT_FOUND_MESSAGE = "Users with ids: [4] not exists"
private const val INVALID_BALANCE_MESSAGE = "No balance found for user with id: 1"

private const val URL_TEMPLATE = "http://localhost:%s/api/v1/users/balance"

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserControllerTest(
        @Autowired
        private val restTemplate: TestRestTemplate,
        @Autowired
        private val userRepository: UserRepository
) {
    @LocalServerPort
    private var port: Int = 0

    @Test
    @DisplayName("Update user balance")
    @Sql(scripts = ["classpath:sql/set-up.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun updateUserBalance() {
        val request = mapOf(
                1 to 100,
                2 to 200,
                3 to 300
        )
        val expectedBalance = 400.00

        val response = restTemplate.postForEntity(String.format(URL_TEMPLATE, port), request, String::class.java)

        assert(response.statusCode.is2xxSuccessful)
        assertEquals(SUCCESS_MESSAGE, response.body)
        assertUsersHaveExpectedBalance(expectedBalance)
    }

    @Test
    @DisplayName("Update user balance negative values")
    @Sql(scripts = ["classpath:sql/set-up.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun updateUserBalanceNegativeValues() {
        val request = mapOf(
                1 to -300,
                2 to -200,
                3 to -100
        )
        val expectedBalance = 0.00

        val response = restTemplate.postForEntity(String.format(URL_TEMPLATE, port), request, String::class.java)

        assert(response.statusCode.is2xxSuccessful)
        assertEquals(SUCCESS_MESSAGE, response.body)
        assertUsersHaveExpectedBalance(expectedBalance)
    }

    @Test
    @DisplayName("Throw exception when id not found")
    @Sql(scripts = ["classpath:sql/set-up.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun userNotFound() {
        val request = mapOf(
                1 to 100,
                2 to 200,
                4 to 300
        )

        val response = restTemplate.postForEntity(String.format(URL_TEMPLATE, port), request, ErrorResponseDto::class.java)

        assert(response.statusCode.is4xxClientError)
        assertEquals(USER_ID_NOT_FOUND_MESSAGE, response.body?.errorMessage)
    }

    @Test
    @DisplayName("Throw exception when balance is missing")
    @Sql(scripts = ["classpath:sql/set-up.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun missingBalance() {
        val request = mapOf(
                1 to null,
                2 to 200,
                3 to 300
        )

        val response = restTemplate.postForEntity(String.format(URL_TEMPLATE, port), request, ErrorResponseDto::class.java)

        assert(response.statusCode.is4xxClientError)
        assertEquals(INVALID_BALANCE_MESSAGE, response.body?.errorMessage)
    }

    private fun assertUsersHaveExpectedBalance(expectedBalance: Double) {
        val users = userRepository.findAll()
        assertTrue(users.all { it.balance == expectedBalance })
    }
}