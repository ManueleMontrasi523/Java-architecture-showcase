package it.marketplace.microservices.config.interceptor;

import it.marketplace.microservices.config.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionCustomHandlerTest {
    private final ExceptionCustomHandler handler = new ExceptionCustomHandler();

    @Test
    void shouldHandleServiceExceptionAndReturnApiError() {
        // Arrange
        ServiceException ex = new ServiceException(ServiceException.ErrorCode.PRODUCT_NOT_FOUND, "Product not found!");
        // Act
        ResponseEntity<ApiError> response = handler.handleGenericException(ex);
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApiError error = response.getBody();
        assertNotNull(error);
        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals(ServiceException.ErrorCode.PRODUCT_NOT_FOUND, error.getCode());
        assertEquals("Product not found!", error.getMessage());
    }
}

