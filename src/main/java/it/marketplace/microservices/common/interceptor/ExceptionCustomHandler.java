package it.marketplace.microservices.common.interceptor;

import it.marketplace.microservices.common.exception.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionCustomHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(UserServiceException ex) {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getErrorCode(), ex.getErrorMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

