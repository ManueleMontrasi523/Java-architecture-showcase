package it.marketplace.microservices.common.resource;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentOrderResourceTest {
    @Test
    void shouldCreatePaymentOrderResourceWithAllFields() {
        // Arrange
        LocalDateTime orderDate = LocalDateTime.now();
        LocalDateTime tmsUpdate = LocalDateTime.now();
        // Act
        PaymentOrderResource resource = new PaymentOrderResource(1L, "ORD123", StatusOrderEnum.PAID, 100.0, 100.0, orderDate, tmsUpdate);
        // Assert
        assertEquals(1L, resource.getId());
        assertEquals("ORD123", resource.getOrderCode());
        assertEquals(StatusOrderEnum.PAID, resource.getStatus());
        assertEquals(100.0, resource.getDebit());
        assertEquals(100.0, resource.getPaid());
        assertEquals(orderDate, resource.getOrderDate());
        assertEquals(tmsUpdate, resource.getTmsUpdate());
    }

    @Test
    void shouldSetAndGetIdAndTmsUpdate() {
        // Arrange
        PaymentOrderResource resource = new PaymentOrderResource();
        LocalDateTime tmsUpdate = LocalDateTime.now();
        // Act
        resource.setId(99L);
        resource.setTmsUpdate(tmsUpdate);
        // Assert
        assertEquals(99L, resource.getId());
        assertEquals(tmsUpdate, resource.getTmsUpdate());
    }
}

