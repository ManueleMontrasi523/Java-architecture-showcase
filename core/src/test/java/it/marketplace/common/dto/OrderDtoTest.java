package it.marketplace.common.dto;

import it.marketplace.common.enums.RoleEnum;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.common.enums.StatusUserEnum;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDtoTest {

    @Test
    public void shouldCreateOrderDtoWithAllFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        UserDto user = new UserDto(1L, "John", "Doe", "john.doe@email.com", "Main Street", "London", StatusUserEnum.ACTIVE, RoleEnum.CLIENT, now, now);
        ProductOrderDto productOrder = new ProductOrderDto(1L, "ORD123", "PROD1", BigDecimal.ONE, 10.0, 10.0, now, now);
        // Act
        OrderDto order = new OrderDto(1L, "ORD123", user, List.of(productOrder), StatusOrderEnum.CREATED, null, now, now);
        // Assert
        assertEquals("1", order.getId().toString());
        assertEquals("ORD123", order.getOrderCode());
        assertEquals(user, order.getUser());
        assertEquals(1, order.getProductOrder().size());
        assertEquals(StatusOrderEnum.CREATED, order.getStatus());
        assertEquals(now, order.getOrderDate());
        assertEquals(now, order.getTmsUpdate());
    }

    @Test
    public void shouldSetAndGetRejectReason() {
        // Arrange
        OrderDto order = new OrderDto();
        // Act
        order.setRejectReason("Out of stock");
        // Assert
        assertEquals("Out of stock", order.getRejectReason());
    }
}

