package it.marketplace.microservices.common.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import it.marketplace.microservices.common.enums.StatusOrderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Resource class representing a payment order in the marketplace system for API responses.
 * Contains payment order details, status, debit, paid amount, and relevant dates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrderResource {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String orderCode;
    private StatusOrderEnum status;
    private Double debit;
    private Double paid;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime orderDate;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime tmsUpdate;

}
