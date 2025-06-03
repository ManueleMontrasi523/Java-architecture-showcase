package it.marketplace.mapper;

import it.marketplace.common.dto.OrderDto;
import it.marketplace.common.dto.ProductOrderDto;
import it.marketplace.common.dto.UserDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.entity.OrderEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderMapperTest {
    @Test
    void shouldMapOrderDtoToEntityAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        UserDto user = new UserDto(2L, "Jane", "Smith", "jane.smith@email.com", "Second Street", "Paris", null, null, now, now);
        ProductOrderDto productOrder = new ProductOrderDto(2L, "ORD456", "PROD2", BigDecimal.TEN, 20.0, 200.0, now, now);
        OrderDto dto = new OrderDto(2L, "ORD456", user, List.of(productOrder), StatusOrderEnum.PAID, null, now, now);
        // Act
        OrderEntity entity = OrderMapper.toEntity(dto);
        OrderDto mappedDto = OrderMapper.toDto(entity);
        // Assert
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getOrderCode(), entity.getOrderCode());
        assertEquals(dto.getOrderDate(), entity.getOrderDate());
        assertEquals(dto.getId(), mappedDto.getId());
        assertEquals(dto.getOrderCode(), mappedDto.getOrderCode());
        assertEquals(dto.getOrderDate(), mappedDto.getOrderDate());
        assertNotNull(entity.getUser());
        assertNotNull(entity.getProductOrder());
    }
}

