package it.marketplace.mapper;

import it.marketplace.common.dto.ProductDto;
import it.marketplace.common.enums.CategoryEnum;
import it.marketplace.entity.ProductEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperTest {
    @Test
    void shouldMapProductDtoToEntityAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        ProductDto dto = new ProductDto(2L, "PROD2", "Product2", "Description2", 19.99, BigDecimal.ONE, CategoryEnum.FOOD, now, now);
        // Act
        ProductEntity entity = ProductMapper.toEntity(dto);
        ProductDto mappedDto = ProductMapper.toDto(entity);
        // Assert
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getProductCode(), entity.getProductCode());
        assertEquals(dto.getCreationDate(), entity.getCreationDate());
        assertEquals(dto.getId(), mappedDto.getId());
        assertEquals(dto.getProductCode(), mappedDto.getProductCode());
        assertEquals(dto.getCreationDate(), mappedDto.getCreationDate());
    }
}

