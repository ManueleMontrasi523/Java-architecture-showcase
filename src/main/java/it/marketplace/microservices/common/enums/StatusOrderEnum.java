package it.marketplace.microservices.common.enums;

import lombok.Getter;

@Getter
public enum StatusOrderEnum {
    CANCELLED,
    CREATED,
    PROCESSING,
    PENDING,
    PAID,
}
