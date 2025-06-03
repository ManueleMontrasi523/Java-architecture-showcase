package it.marketplace.microservices.database.entity;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaymentInstallmentsEntityTest {

    @Test
    void shouldCreateEntityWithAllArgsConstructor_ArrangeActAssert() {
        // Arrange/Act
        LocalDateTime now = LocalDateTime.now();
        PaymentInstallmentsEntity entity = new PaymentInstallmentsEntity(1L, "REF1", "ORD1", StatusOrderEnum.PAID, 100.0, now);
        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("REF1", entity.getReference());
        assertEquals("ORD1", entity.getOrderCode());
        assertEquals(StatusOrderEnum.PAID, entity.getStatus());
        assertEquals(100.0, entity.getDebit());
        assertEquals(now, entity.getTmsUpdate());
    }

    @Test
    void shouldSetAndGetFields_ArrangeActAssert() {
        // Arrange
        PaymentInstallmentsEntity entity = new PaymentInstallmentsEntity();
        LocalDateTime now = LocalDateTime.now();
        // Act
        entity.setId(2L);
        entity.setReference("REF2");
        entity.setOrderCode("ORD2");
        entity.setStatus(StatusOrderEnum.CREATED);
        entity.setDebit(200.0);
        entity.setTmsUpdate(now);
        // Assert
        assertEquals(2L, entity.getId());
        assertEquals("REF2", entity.getReference());
        assertEquals("ORD2", entity.getOrderCode());
        assertEquals(StatusOrderEnum.CREATED, entity.getStatus());
        assertEquals(200.0, entity.getDebit());
        assertEquals(now, entity.getTmsUpdate());
    }

    @Test
    void shouldTestEqualsAndHashCode_ArrangeActAssert() {
        // Arrange
        PaymentInstallmentsEntity e1 = new PaymentInstallmentsEntity();
        PaymentInstallmentsEntity e2 = new PaymentInstallmentsEntity();
        // Act
        e1.setId(1L); e2.setId(1L);
        e1.setReference("REF"); e2.setReference("REF");
        // Assert
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void shouldTestToString_ArrangeActAssert() {
        // Arrange
        PaymentInstallmentsEntity entity = new PaymentInstallmentsEntity();
        entity.setReference("REF");
        // Act
        String str = entity.toString();
        // Assert
        assertTrue(str.contains("REF"));
    }
}

