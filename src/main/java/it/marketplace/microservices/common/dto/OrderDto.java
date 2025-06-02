package it.marketplace.microservices.common.dto;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing an order in the marketplace system.
 * Contains information about the order, user, products, status, and relevant dates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private String orderCode;
    private UserDto user;
    private List<ProductOrderDto> productOrder;
    private StatusOrderEnum status;
    private String rejectReason;

    private LocalDateTime orderDate;
    private LocalDateTime tmsUpdate;

}
