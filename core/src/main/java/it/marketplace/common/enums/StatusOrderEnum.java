package it.marketplace.common.enums;

import lombok.Getter;

/**
 * Enum representing the possible statuses of an order in the marketplace system.
 */
@Getter
public enum StatusOrderEnum {
    CANCELLED,
    CREATED,
    PROCESSING,
    PENDING_PAYMENT,
    RATEIZED,
    PAID,
    REJECTED,
    FAILED
}
