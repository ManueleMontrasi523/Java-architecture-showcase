package it.marketplace.microservices.common.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import it.marketplace.microservices.common.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResource {

    private String productCode;
    private String name;
    private String description;
    private double PRICE;
    private String supply;
    private CategoryEnum category;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime creationDate;

}
