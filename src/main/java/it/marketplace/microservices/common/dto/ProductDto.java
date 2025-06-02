package it.marketplace.microservices.common.dto;

import it.marketplace.microservices.common.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a product in the marketplace system.
 * Contains product details such as code, name, description, price, supply, category, and relevant dates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String productCode;
    private String name;
    private String description;
    private double PRICE;
    private BigDecimal supply;
    private CategoryEnum category;

    private LocalDateTime creationDate;
    private LocalDateTime tmsUpdate;

}
