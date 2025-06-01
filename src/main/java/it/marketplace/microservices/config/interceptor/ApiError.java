package it.marketplace.microservices.config.interceptor;

import it.marketplace.microservices.config.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private int status;
    private ServiceException.ErrorCode code;
    private String message;
}