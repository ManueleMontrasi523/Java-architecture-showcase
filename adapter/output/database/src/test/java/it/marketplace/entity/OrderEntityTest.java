package it.marketplace.entity;

import it.marketplace.common.enums.StatusOrderEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderEntityTest {

    @Test
    void shouldCreateEntityWithAllArgsConstructor_ArrangeActAssert() {
        // Arrange/Act
        UserEntity user = new UserEntity();
        ProductOrderEntity productOrder = new ProductOrderEntity();
        LocalDateTime now = LocalDateTime.now();
        OrderEntity entity = new OrderEntity(1L, "ORD123", user, List.of(productOrder), StatusOrderEnum.CREATED, "reason", now, now);
        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("ORD123", entity.getOrderCode());
        assertEquals(user, entity.getUser());
        assertEquals(1, entity.getProductOrder().size());
        assertEquals(StatusOrderEnum.CREATED, entity.getStatus());
        assertEquals("reason", entity.getRejectReason());
        assertEquals(now, entity.getOrderDate());
        assertEquals(now, entity.getTmsUpdate());
    }

    @Test
    void shouldSetAndGetFields_ArrangeActAssert() {
        // Arrange
        OrderEntity entity = new OrderEntity();
        UserEntity user = new UserEntity();
        ProductOrderEntity productOrder = new ProductOrderEntity();
        LocalDateTime now = LocalDateTime.now();
        // Act
        entity.setId(2L);
        entity.setOrderCode("ORD456");
        entity.setUser(user);
        entity.setProductOrder(List.of(productOrder));
        entity.setStatus(StatusOrderEnum.REJECTED);
        entity.setRejectReason("bad reason");
        entity.setOrderDate(now);
        entity.setTmsUpdate(now);
        // Assert
        assertEquals(2L, entity.getId());
        assertEquals("ORD456", entity.getOrderCode());
        assertEquals(user, entity.getUser());
        assertEquals(1, entity.getProductOrder().size());
        assertEquals(StatusOrderEnum.REJECTED, entity.getStatus());
        assertEquals("bad reason", entity.getRejectReason());
        assertEquals(now, entity.getOrderDate());
        assertEquals(now, entity.getTmsUpdate());
    }

    @Test
    void shouldTestEqualsAndHashCode_ArrangeActAssert() {
        // Arrange
        OrderEntity e1 = new OrderEntity();
        OrderEntity e2 = new OrderEntity();
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
        OrderEntity entity = new OrderEntity();
        entity.setOrderCode("ORD");
        // Act
        String str = entity.toString();
        // Assert
        assertTrue(str.contains("ORD"));
    }
}

