package it.marketplace.microservices.config.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ServiceException extends RuntimeException {
    private ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public enum ErrorCode {
        GENERIC_ERROR,
        DATA_ALREADY_PRESENT,
        EMAIL_NOT_FOUND,
        PRODUCT_NOT_FOUND,
        PRODUCT_SUPPLY_EXCEED,
        ORDER_NOT_FOUND,
        ORDER_EXIST_FOR_USER_FOUND
    }
}
