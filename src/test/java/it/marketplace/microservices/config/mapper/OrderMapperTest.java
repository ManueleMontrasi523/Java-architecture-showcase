package it.marketplace.microservices.config.mapper;

import it.marketplace.microservices.common.dto.OrderDto;
import it.marketplace.microservices.common.dto.ProductOrderDto;
import it.marketplace.microservices.common.dto.UserDto;
import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.common.resource.OrderResource;
import it.marketplace.microservices.database.entity.OrderEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {
    @Test
    void shouldMapOrderDtoToResourceAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        UserDto user = new UserDto(1L, "John", "Doe", "john.doe@email.com", "Main Street", "London", null, null, now);
        ProductOrderDto productOrder = new ProductOrderDto(1L, "ORD123", "PROD1", BigDecimal.ONE, 10.0, 10.0, now, now);
        OrderDto dto = new OrderDto(1L, "ORD123", user, List.of(productOrder), StatusOrderEnum.CREATED, null, now, now);
        // Act
        OrderResource resource = OrderMapper.toResource(dto);
        OrderDto mappedDto = OrderMapper.toDto(resource);
        // Assert
        assertEquals(dto.getId(), resource.getId());
        assertEquals(dto.getOrderCode(), resource.getOrderCode());
        assertEquals(dto.getOrderDate(), resource.getOrderDate());
        assertEquals(dto.getId(), mappedDto.getId());
        assertEquals(dto.getOrderCode(), mappedDto.getOrderCode());
        assertEquals(dto.getOrderDate(), mappedDto.getOrderDate());
        assertNotNull(resource.getUserResource());
        assertNotNull(resource.getProductResource());
    }

    @Test
    void shouldMapOrderDtoToEntityAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        UserDto user = new UserDto(2L, "Jane", "Smith", "jane.smith@email.com", "Second Street", "Paris", null, null, now);
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

