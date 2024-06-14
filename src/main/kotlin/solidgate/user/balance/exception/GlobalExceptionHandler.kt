package solidgate.user.balance.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import solidgate.user.balance.dto.ErrorResponseDto
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleUserValidationException(ex: UserNotFoundException): ResponseEntity<ErrorResponseDto> {
        val errorResponseDto = ErrorResponseDto(ex.message, LocalDateTime.now())
        return ResponseEntity.badRequest().body(errorResponseDto)
    }

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<ErrorResponseDto> {
        val errorResponseDto = ErrorResponseDto(ex.message, LocalDateTime.now())
        return ResponseEntity.badRequest().body(errorResponseDto)
    }
}