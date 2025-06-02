package it.marketplace.microservices.common.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductOrderDtoTest {

    @Test
    void shouldCreateProductOrderDtoWithAllFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        // Act
        ProductOrderDto dto = new ProductOrderDto(1L, "ORD123", "PROD1", BigDecimal.ONE, 10.0, 10.0, now, now);
        // Assert
        assertEquals("1", dto.getId().toString());
        assertEquals("ORD123", dto.getOrderCode());
        assertEquals("PROD1", dto.getProductCode());
        assertEquals(BigDecimal.ONE, dto.getQuantity());
        assertEquals(10.0, dto.getUnitPrice());
        assertEquals(10.0, dto.getTotal());
        assertEquals(now, dto.getCreationDate());
        assertEquals(now, dto.getTmsUpdate());
    }

    @Test
    void shouldSetAndGetTotal() {
        // Arrange
        ProductOrderDto dto = new ProductOrderDto();
        // Act
        dto.setTotal(99.99);
        // Assert
        assertEquals(99.99, dto.getTotal());
    }
}

