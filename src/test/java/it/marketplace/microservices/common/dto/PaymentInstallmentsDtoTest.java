package it.marketplace.microservices.common.dto;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PaymentInstallmentsDtoTest {

    @Test
    void shouldCreatePaymentInstallmentsDtoWithAllFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        // Act
        PaymentInstallmentsDto dto = new PaymentInstallmentsDto(1L, "REF123", "ORD123", StatusOrderEnum.PAID, 100.0, now);
        // Assert
        assertEquals("1", dto.getId().toString());
        assertEquals("REF123", dto.getReference());
        assertEquals("ORD123", dto.getOrderCode());
        assertEquals(StatusOrderEnum.PAID, dto.getStatus());
        assertEquals(100.0, dto.getDebit());
        assertEquals(now, dto.getTmsUpdate());
    }

    @Test
    void shouldSetAndGetDebit() {
        // Arrange
        PaymentInstallmentsDto dto = new PaymentInstallmentsDto();
        // Act
        dto.setDebit(250.0);
        // Assert
        assertEquals(250.0, dto.getDebit());
    }
}

