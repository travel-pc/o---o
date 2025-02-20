package io.travia.core.api

import io.travia.core.support.error.ErrorType
import io.travia.core.support.response.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.logging.Logger

@RestControllerAdvice
class ApiControllerAdvice {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Any>> {
        log.error("Exception : {}", e.message, e)
        return ResponseEntity(ApiResponse.error(ErrorType.DEFAULT_ERROR), ErrorType.DEFAULT_ERROR.status)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(e: NoSuchElementException): ResponseEntity<ApiResponse<Any>> {
        log.error("NoSuchElementException : {}", e.message, e)
        return ResponseEntity(ApiResponse.error(ErrorType.NOTFOUND_ERROR), ErrorType.NOTFOUND_ERROR.status)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): ResponseEntity<ApiResponse<Any>> {
        log.error("IllegalStateException : {}", e.message, e)
        return ResponseEntity(ApiResponse.error(ErrorType.BAD_REQUEST_ERROR), ErrorType.BAD_REQUEST_ERROR.status)
    }
}