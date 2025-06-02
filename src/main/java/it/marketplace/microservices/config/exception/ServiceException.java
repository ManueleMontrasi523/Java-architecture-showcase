package it.marketplace.microservices.config.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Custom exception for service layer errors in the marketplace system.
 * Includes an error code and message for detailed error handling.
 */
@Getter
@NoArgsConstructor
public class ServiceException extends RuntimeException {
    /**
     * The error code associated with this exception.
     */
    private ErrorCode errorCode;

    /**
     * Constructs a new ServiceException with the specified error code and message.
     *
     * @param errorCode the error code representing the type of error
     * @param errorMessage the detailed error message
     */
    public ServiceException(ErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    /**
     * Enum representing possible error codes for service exceptions.
     */
    public enum ErrorCode {
        GENERIC_ERROR,
        DATA_ALREADY_PRESENT,
        EMAIL_NOT_FOUND,
        PRODUCT_NOT_FOUND,
        PRODUCT_SUPPLY_EXCEED,
        ORDER_NOT_FOUND,
        ORDER_EXIST_FOR_USER_FOUND,
        PAYMENT_RATE_EXCEED
    }
}
