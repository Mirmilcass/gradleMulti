package edu.mirmil.web.exception

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.method.HandlerMethod

@ControllerAdvice
@RestController
class CustomRestExceptionHandler {

    private val log = KotlinLogging.logger { }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun methodArgumentNotValidException(
        exception: MethodArgumentNotValidException,
        hm: HandlerMethod
    ): ResponseEntity<MutableMap<String, Any?>> {
        val error = exception.bindingResult.fieldError
        val errorMap: MutableMap<String, Any?> = mutableMapOf(
            "field" to error?.field,
            "rejectedValue" to error?.rejectedValue,
            "message" to error?.defaultMessage
        )
        log.info {
            "\n----------\n${hm.beanType.simpleName}#${hm.method.name}\n" +
                    "ErrorContent :: $errorMap\n----------\n"
        }
        return ResponseEntity(errorMap, HttpStatus.BAD_REQUEST)
    }
}