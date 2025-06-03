package it.marketplace.common.dto;

import it.marketplace.common.enums.StatusOrderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing payment installments for an order.
 * Contains information about the installment reference, order code, status, debit amount, and update timestamp.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInstallmentsDto {

    private Long id;

    private String reference;
    private String orderCode;
    private StatusOrderEnum status;
    private Double debit;

    private LocalDateTime tmsUpdate;

}
