package it.marketplace.microservices.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
