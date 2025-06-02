package it.marketplace.microservices.common.resource;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentInstallmentsResourceTest {
    @Test
    void shouldCreatePaymentInstallmentsResourceWithAllFields() {
        // Arrange
        LocalDateTime tmsUpdate = LocalDateTime.now();
        // Act
        PaymentInstallmentsResource resource = new PaymentInstallmentsResource(1L, "REF123", "ORD123", StatusOrderEnum.PAID, 100.0, tmsUpdate);
        // Assert
        assertEquals(1L, resource.getId());
        assertEquals("REF123", resource.getReference());
        assertEquals("ORD123", resource.getOrderCode());
        assertEquals(StatusOrderEnum.PAID, resource.getStatus());
        assertEquals(100.0, resource.getDebit());
        assertEquals(tmsUpdate, resource.getTmsUpdate());
    }

    @Test
    void shouldSetAndGetIdAndTmsUpdate() {
        // Arrange
        PaymentInstallmentsResource resource = new PaymentInstallmentsResource();
        LocalDateTime tmsUpdate = LocalDateTime.now();
        // Act
        resource.setId(42L);
        resource.setTmsUpdate(tmsUpdate);
        // Assert
        assertEquals(42L, resource.getId());
        assertEquals(tmsUpdate, resource.getTmsUpdate());
    }
}

