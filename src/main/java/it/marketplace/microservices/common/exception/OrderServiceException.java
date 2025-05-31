package it.marketplace.microservices.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderServiceException extends Exception {
    private ErrorCode errorCode;
    private String errorMessage;

    public OrderServiceException(ErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public enum ErrorCode {
        GENERIC_ERROR,
        DATA_ALREADY_PRESENT,
        ORDER_NOT_FOUND
    }
}