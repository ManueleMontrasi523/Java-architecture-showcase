package it.marketplace.microservices.common.enums;

import lombok.Getter;

@Getter
public enum StatusOrderEnum {
    CREATED,
    CANCELLED,
    PENDING,
    PAID,
}
