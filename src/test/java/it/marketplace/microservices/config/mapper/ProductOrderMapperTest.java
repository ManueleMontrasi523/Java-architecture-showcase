package it.marketplace.microservices.config.mapper;

import it.marketplace.microservices.common.dto.ProductOrderDto;
import it.marketplace.microservices.common.resource.ProductOrderResource;
import it.marketplace.microservices.database.entity.ProductOrderEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductOrderMapperTest {
    @Test
    void shouldMapProductOrderDtoToResourceAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        ProductOrderDto dto = new ProductOrderDto(1L, "ORD123", "PROD1", BigDecimal.ONE, 10.0, 10.0, now, now);
        // Act
        ProductOrderResource resource = ProductOrderMapper.toResource(dto);
        ProductOrderDto mappedDto = ProductOrderMapper.toDto(resource);
        // Assert
        assertEquals(dto.getOrderCode(), resource.getOrderCode());
        assertEquals(dto.getProductCode(), resource.getProductCode());
        assertEquals(dto.getCreationDate(), resource.getCreationDate());
        assertEquals(dto.getOrderCode(), mappedDto.getOrderCode());
        assertEquals(dto.getProductCode(), mappedDto.getProductCode());
        assertEquals(dto.getCreationDate(), mappedDto.getCreationDate());
    }

    @Test
    void shouldMapProductOrderDtoToEntityAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        ProductOrderDto dto = new ProductOrderDto(2L, "ORD456", "PROD2", BigDecimal.TEN, 20.0, 200.0, now, now);
        // Act
        ProductOrderEntity entity = ProductOrderMapper.toEntity(dto);
        ProductOrderDto mappedDto = ProductOrderMapper.toDto(entity);
        // Assert
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getOrderCode(), entity.getOrderCode());
        assertEquals(dto.getProductCode(), entity.getProductCode());
        assertEquals(dto.getCreationDate(), entity.getCreationDate());
        assertEquals(dto.getId(), mappedDto.getId());
        assertEquals(dto.getOrderCode(), mappedDto.getOrderCode());
        assertEquals(dto.getProductCode(), mappedDto.getProductCode());
        assertEquals(dto.getCreationDate(), mappedDto.getCreationDate());
    }
}

