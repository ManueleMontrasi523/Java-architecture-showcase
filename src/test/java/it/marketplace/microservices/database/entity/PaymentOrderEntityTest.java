package it.marketplace.microservices.database.entity;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaymentOrderEntityTest {

    @Test
    void shouldCreateEntityWithAllArgsConstructor_ArrangeActAssert() {
        // Arrange/Act
        LocalDateTime now = LocalDateTime.now();
        PaymentOrderEntity entity = new PaymentOrderEntity(1L, "ORD1", StatusOrderEnum.PAID, 100.0, now, now);
        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("ORD1", entity.getOrderCode());
        assertEquals(StatusOrderEnum.PAID, entity.getStatus());
        assertEquals(100.0, entity.getDebit());
        assertEquals(now, entity.getOrderDate());
        assertEquals(now, entity.getTmsUpdate());
    }

    @Test
    void shouldSetAndGetFields_ArrangeActAssert() {
        // Arrange
        PaymentOrderEntity entity = new PaymentOrderEntity();
        LocalDateTime now = LocalDateTime.now();
        // Act
        entity.setId(2L);
        entity.setOrderCode("ORD2");
        entity.setStatus(StatusOrderEnum.CREATED);
        entity.setDebit(200.0);
        entity.setOrderDate(now);
        entity.setTmsUpdate(now);
        // Assert
        assertEquals(2L, entity.getId());
        assertEquals("ORD2", entity.getOrderCode());
        assertEquals(StatusOrderEnum.CREATED, entity.getStatus());
        assertEquals(200.0, entity.getDebit());
        assertEquals(now, entity.getOrderDate());
        assertEquals(now, entity.getTmsUpdate());
    }

    @Test
    void shouldTestEqualsAndHashCode_ArrangeActAssert() {
        // Arrange
        PaymentOrderEntity e1 = new PaymentOrderEntity();
        PaymentOrderEntity e2 = new PaymentOrderEntity();
        // Act
        e1.setId(1L); e2.setId(1L);
        e1.setOrderCode("ORD"); e2.setOrderCode("ORD");
        // Assert
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void shouldTestToString_ArrangeActAssert() {
        // Arrange
        PaymentOrderEntity entity = new PaymentOrderEntity();
        entity.setOrderCode("ORD");
        // Act
        String str = entity.toString();
        // Assert
        assertTrue(str.contains("ORD"));
    }
}

