package it.marketplace.microservices.common.dto;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a payment order in the marketplace system.
 * Contains information about the payment order, including status, debit, paid amount, and relevant dates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrderDto {

    private Long id;

    private String orderCode;
    private StatusOrderEnum status;
    private Double debit;
    private Double paid;

    private LocalDateTime orderDate;
    private LocalDateTime tmsUpdate;

}
