package it.marketplace.microservices.common.dto;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentOrderDtoTest {

    @Test
    void shouldCreatePaymentOrderDtoWithAllFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        // Act
        PaymentOrderDto dto = new PaymentOrderDto(1L, "ORD123", StatusOrderEnum.PAID, 100.0, 100.0, now, now);
        // Assert
        assertEquals("1", dto.getId().toString());
        assertEquals("ORD123", dto.getOrderCode());
        assertEquals(StatusOrderEnum.PAID, dto.getStatus());
        assertEquals(100.0, dto.getDebit());
        assertEquals(100.0, dto.getPaid());
        assertEquals(now, dto.getOrderDate());
        assertEquals(now, dto.getTmsUpdate());
    }

    @Test
    void shouldSetAndGetPaid() {
        // Arrange
        PaymentOrderDto dto = new PaymentOrderDto();
        // Act
        dto.setPaid(50.0);
        // Assert
        assertEquals(50.0, dto.getPaid());
    }
}

