package it.marketplace.microservices.common.resource;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Resource class representing payment installments for an order in API responses.
 * Contains installment reference, order code, status, debit amount, and update timestamp.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInstallmentsResource {

    private Long id;

    private String reference;
    private String orderCode;
    private StatusOrderEnum status;
    private Double debit;

    private LocalDateTime tmsUpdate;

}
