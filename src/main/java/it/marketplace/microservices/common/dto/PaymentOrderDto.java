package it.marketplace.microservices.common.dto;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrderDto {

    private Long id;

    private String orderCode;
    private StatusOrderEnum status;
    private Double debit;
    private Double paid;

    private LocalDateTime orderDate;
    private LocalDateTime tmsUpdate;

}
