package io.travia.core.support.error

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ErrorType(val status: HttpStatus, val code: ErrorCode, val message: String, val logLevel: LogLevel) {
    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "An unexpected error has occurred.", LogLevel.ERROR),
    NOTFOUND_ERROR(HttpStatus.NOT_FOUND, ErrorCode.E404, "not found resource.", LogLevel.ERROR),
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "bad request", LogLevel.ERROR),
}