package it.marketplace.microservices.config.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceExceptionTest {
    @Test
    void shouldCreateServiceExceptionWithErrorCodeAndMessage() {
        // Arrange
        ServiceException.ErrorCode code = ServiceException.ErrorCode.PRODUCT_NOT_FOUND;
        String message = "Product not found!";
        // Act
        ServiceException ex = new ServiceException(code, message);
        // Assert
        assertEquals(code, ex.getErrorCode());
        assertEquals(message, ex.getMessage());
    }

    @Test
    void shouldCreateServiceExceptionWithNoArgs() {
        // Act
        ServiceException ex = new ServiceException();
        // Assert
        assertNull(ex.getErrorCode());
        assertNull(ex.getMessage());
    }

    @Test
    void shouldContainAllErrorCodes() {
        // Act & Assert
        assertNotNull(ServiceException.ErrorCode.GENERIC_ERROR);
        assertNotNull(ServiceException.ErrorCode.DATA_ALREADY_PRESENT);
        assertNotNull(ServiceException.ErrorCode.EMAIL_NOT_FOUND);
        assertNotNull(ServiceException.ErrorCode.PRODUCT_NOT_FOUND);
        assertNotNull(ServiceException.ErrorCode.PRODUCT_SUPPLY_EXCEED);
        assertNotNull(ServiceException.ErrorCode.ORDER_NOT_FOUND);
        assertNotNull(ServiceException.ErrorCode.ORDER_EXIST_FOR_USER_FOUND);
        assertNotNull(ServiceException.ErrorCode.PAYMENT_RATE_EXCEED);
    }
}

