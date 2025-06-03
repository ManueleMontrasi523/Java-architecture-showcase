package it.marketplace.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a product order in the marketplace system.
 * Contains details about the product ordered, quantity, pricing, and relevant dates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDto {

    private Long id;
    private String orderCode;
    private String productCode;
    private BigDecimal quantity;
    private Double unitPrice;
    private Double total;

    private LocalDateTime creationDate;
    private LocalDateTime tmsUpdate;

}
