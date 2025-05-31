package it.marketplace.microservices.common.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.database.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResource {

    private String orderCode;
    private UserEntity name;
    private StatusOrderEnum status;
    private Integer quantity;
    private Double unitPrice;
    private Double total;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime orderDate;

}
