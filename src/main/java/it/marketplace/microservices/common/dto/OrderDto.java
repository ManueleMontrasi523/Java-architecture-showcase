package it.marketplace.microservices.common.dto;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.database.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private String orderCode;
    private UserEntity name;
    private StatusOrderEnum status;
    private Integer quantity;
    private Double unitPrice;
    private Double total;

    private LocalDateTime orderDate;
    private LocalDateTime tmsUpdate;

}
