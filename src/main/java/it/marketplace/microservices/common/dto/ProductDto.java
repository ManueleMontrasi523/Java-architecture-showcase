package it.marketplace.microservices.common.dto;

import it.marketplace.microservices.common.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String productCode;
    private String name;
    private String description;
    private double PRICE;
    private String supply;
    private CategoryEnum category;

    private LocalDateTime creationDate;
    private LocalDateTime tmsUpdate;

}
