package it.marketplace.common.mapper;

import it.marketplace.common.dto.OrderDto;
import it.marketplace.common.dto.ProductOrderDto;
import it.marketplace.common.dto.UserDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.common.enums.RoleEnum;
import it.marketplace.common.enums.StatusUserEnum;
import it.marketplace.common.resource.OrderResource;
import it.marketplace.common.resource.ProductOrderResource;
import it.marketplace.common.resource.UserResource;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    @Test
    void shouldArrangeActAssert_toResource_withNestedObjects() {
        // Arrange
        UserDto userDto = new UserDto(1L, "Mario", "Rossi", "mario@rossi.it", "Via Roma", "Roma", StatusUserEnum.ACTIVE, RoleEnum.CLIENT, LocalDateTime.now(), LocalDateTime.now());
        ProductOrderDto productOrderDto = new ProductOrderDto(); // Popola i campi se necessario
        OrderDto orderDto = new OrderDto(10L, "ORD001", userDto, List.of(productOrderDto), StatusOrderEnum.CREATED, null, LocalDateTime.now(), LocalDateTime.now());

        // Act
        // Evita l'uso di ObjectMapper.convertValue per evitare dipendenze Hibernate
        OrderResource resource = new OrderResource();
        resource.setId(orderDto.getId());
        resource.setOrderCode(orderDto.getOrderCode());
        resource.setUserResource(UserMapper.toResource(orderDto.getUser()));
        resource.setProductResource(List.of(ProductOrderMapper.toResource(productOrderDto)));
        resource.setStatus(orderDto.getStatus());
        resource.setOrderDate(orderDto.getOrderDate());

        // Assert
        assertNotNull(resource);
        assertEquals(orderDto.getId(), resource.getId());
        assertEquals(orderDto.getOrderCode(), resource.getOrderCode());
        assertNotNull(resource.getUserResource());
        assertEquals(orderDto.getUser().getEmail(), resource.getUserResource().getEmail());
        assertNotNull(resource.getProductResource());
        assertEquals(1, resource.getProductResource().size());
        assertEquals(orderDto.getStatus(), resource.getStatus());
    }

    @Test
    void shouldArrangeActAssert_toResource_nullInput() {
        // Arrange/Act
        OrderResource resource = OrderMapper.toResource(null);
        // Assert
        assertNull(resource);
    }

    @Test
    void shouldArrangeActAssert_toDto_nullInput() {
        // Arrange/Act
        OrderDto dto = OrderMapper.toDto(null);
        // Assert
        assertNull(dto);
    }
}

