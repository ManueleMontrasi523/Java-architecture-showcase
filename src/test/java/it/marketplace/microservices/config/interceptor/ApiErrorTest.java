package it.marketplace.microservices.config.interceptor;

import it.marketplace.microservices.config.exception.ServiceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiErrorTest {
    @Test
    void shouldCreateApiErrorWithAllFields() {
        // Arrange
        int status = 400;
        ServiceException.ErrorCode code = ServiceException.ErrorCode.GENERIC_ERROR;
        String message = "An error occurred";
        // Act
        ApiError error = new ApiError(status, code, message);
        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertEquals(message, error.getMessage());
    }

    @Test
    void shouldSetAndGetFields() {
        // Arrange
        ApiError error = new ApiError();
        int status = 404;
        ServiceException.ErrorCode code = ServiceException.ErrorCode.PRODUCT_NOT_FOUND;
        String message = "Product not found";
        // Act
        error.setStatus(status);
        error.setCode(code);
        error.setMessage(message);
        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertEquals(message, error.getMessage());
    }
}

