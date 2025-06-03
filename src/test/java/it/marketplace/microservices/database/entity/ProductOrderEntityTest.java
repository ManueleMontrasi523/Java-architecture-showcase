package it.marketplace.microservices.database.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductOrderEntityTest {

    @Test
    void shouldCreateEntityWithAllArgsConstructor_ArrangeActAssert() {
        // Arrange/Act
        LocalDateTime now = LocalDateTime.now();
        ProductOrderEntity entity = new ProductOrderEntity(1L, "ORD1", "PROD1", BigDecimal.ONE, 10.0, 10.0, now, now);
        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("ORD1", entity.getOrderCode());
        assertEquals("PROD1", entity.getProductCode());
        assertEquals(BigDecimal.ONE, entity.getQuantity());
        assertEquals(10.0, entity.getUnitPrice());
        assertEquals(10.0, entity.getTotal());
        assertEquals(now, entity.getCreationDate());
        assertEquals(now, entity.getTmsUpdate());
    }

    @Test
    void shouldSetAndGetFields_ArrangeActAssert() {
        // Arrange
        ProductOrderEntity entity = new ProductOrderEntity();
        LocalDateTime now = LocalDateTime.now();
        // Act
        entity.setId(2L);
        entity.setOrderCode("ORD2");
        entity.setProductCode("PROD2");
        entity.setQuantity(BigDecimal.TEN);
        entity.setUnitPrice(20.0);
        entity.setTotal(200.0);
        entity.setCreationDate(now);
        entity.setTmsUpdate(now);
        // Assert
        assertEquals(2L, entity.getId());
        assertEquals("ORD2", entity.getOrderCode());
        assertEquals("PROD2", entity.getProductCode());
        assertEquals(BigDecimal.TEN, entity.getQuantity());
        assertEquals(20.0, entity.getUnitPrice());
        assertEquals(200.0, entity.getTotal());
        assertEquals(now, entity.getCreationDate());
        assertEquals(now, entity.getTmsUpdate());
    }

    @Test
    void shouldTestEqualsAndHashCode_ArrangeActAssert() {
        // Arrange
        ProductOrderEntity e1 = new ProductOrderEntity();
        ProductOrderEntity e2 = new ProductOrderEntity();
        // Act
        e1.setId(1L); e2.setId(1L);
        e1.setOrderCode("O"); e2.setOrderCode("O");
        // Assert
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void shouldTestToString_ArrangeActAssert() {
        // Arrange
        ProductOrderEntity entity = new ProductOrderEntity();
        entity.setOrderCode("O");
        // Act
        String str = entity.toString();
        // Assert
        assertTrue(str.contains("O"));
    }
}

