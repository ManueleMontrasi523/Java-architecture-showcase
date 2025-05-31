package it.marketplace.microservices.common.interceptor;

import it.marketplace.microservices.common.exception.UserServiceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private int status;
    private UserServiceException.ErrorCode code;
    private String message;
}