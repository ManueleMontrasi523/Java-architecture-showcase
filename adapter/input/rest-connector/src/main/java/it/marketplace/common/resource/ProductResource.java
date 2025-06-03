package it.marketplace.common.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import it.marketplace.common.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Resource class representing a product in the marketplace system for API responses.
 * Contains product details such as code, name, description, price, supply, category, and creation date.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResource {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String productCode;
    private String name;
    private String description;
    private double price;
    private BigDecimal supply;
    private CategoryEnum category;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime creationDate;

}
