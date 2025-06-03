package it.marketplace.common.interceptor;

import it.marketplace.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an API error response for the marketplace system.
 * Contains the HTTP status, error code, and error message to be returned to the client.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private int status;
    private ServiceException.ErrorCode code;
    private String message;
}