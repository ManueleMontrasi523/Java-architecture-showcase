package it.marketplace.microservices.common.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
