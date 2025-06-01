package it.marketplace.microservices.config.interceptor;

import it.marketplace.microservices.config.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionCustomHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiError> handleGenericException(ServiceException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError error = new ApiError(
                status.value(),
                ex.getErrorCode(),
                ex.getMessage()
        );
        return ResponseEntity.status(status).body(error);
    }
}

