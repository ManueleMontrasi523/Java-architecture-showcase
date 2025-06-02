package it.marketplace.microservices.common.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Resource class representing a product order in the marketplace system for API responses.
 * Contains details about the ordered product, quantity, pricing, and relevant dates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderResource {

    private String orderCode;
    private String productCode;
    private BigDecimal quantity;
    private Double unitPrice;
    private Double total;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime creationDate;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime tmsUpdate;

}
