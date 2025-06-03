package it.marketplace.common.resource;

import it.marketplace.common.enums.StatusOrderEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderResourceTest {
    @Test
    void shouldCreateOrderResourceWithAllFields() {
        // Arrange
        LocalDateTime orderDate = LocalDateTime.now();
        UserResource user = new UserResource(1L, "John", "Doe", "john.doe@email.com", "Main Street", "London", null, null, null);
        ProductOrderResource productOrder = new ProductOrderResource("ORD123", "PROD1", BigDecimal.ONE, 10.0, 10.0, orderDate, orderDate);
        // Act
        OrderResource resource = new OrderResource(1L, "ORD123", user, List.of(productOrder), StatusOrderEnum.CREATED, orderDate);
        // Assert
        assertEquals(1L, resource.getId());
        assertEquals("ORD123", resource.getOrderCode());
        assertEquals(user, resource.getUserResource());
        assertEquals(1, resource.getProductResource().size());
        assertEquals(StatusOrderEnum.CREATED, resource.getStatus());
        assertEquals(orderDate, resource.getOrderDate());
    }

    @Test
    void shouldSetAndGetIdAndOrderDate() {
        // Arrange
        OrderResource resource = new OrderResource();
        LocalDateTime orderDate = LocalDateTime.now();
        // Act
        resource.setId(77L);
        resource.setOrderDate(orderDate);
        // Assert
        assertEquals(77L, resource.getId());
        assertEquals(orderDate, resource.getOrderDate());
    }
}

