package it.marketplace.common.mapper;

import it.marketplace.common.dto.ProductOrderDto;
import it.marketplace.common.resource.ProductOrderResource;
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

}

