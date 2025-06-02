package it.marketplace.microservices.common.resource;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductOrderResourceTest {
    @Test
    void shouldCreateProductOrderResourceWithAllFields() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime tmsUpdate = LocalDateTime.now();
        // Act
        ProductOrderResource resource = new ProductOrderResource("ORD123", "PROD1", BigDecimal.ONE, 10.0, 10.0, creationDate, tmsUpdate);
        // Assert
        assertEquals("ORD123", resource.getOrderCode());
        assertEquals("PROD1", resource.getProductCode());
        assertEquals(BigDecimal.ONE, resource.getQuantity());
        assertEquals(10.0, resource.getUnitPrice());
        assertEquals(10.0, resource.getTotal());
        assertEquals(creationDate, resource.getCreationDate());
        assertEquals(tmsUpdate, resource.getTmsUpdate());
    }

    @Test
    void shouldSetAndGetTmsUpdate() {
        // Arrange
        ProductOrderResource resource = new ProductOrderResource();
        LocalDateTime tmsUpdate = LocalDateTime.now();
        // Act
        resource.setTmsUpdate(tmsUpdate);
        // Assert
        assertEquals(tmsUpdate, resource.getTmsUpdate());
    }
}

