package solidgate.user.balance.dto

import java.time.LocalDateTime

data class ErrorResponseDto(
        val errorMessage: String?,
        val time: LocalDateTime?
) {
}