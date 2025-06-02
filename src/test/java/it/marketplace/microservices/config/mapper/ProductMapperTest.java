package it.marketplace.microservices.config.mapper;

import it.marketplace.microservices.common.dto.ProductDto;
import it.marketplace.microservices.common.resource.ProductResource;
import it.marketplace.microservices.database.entity.ProductEntity;
import it.marketplace.microservices.common.enums.CategoryEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperTest {
    @Test
    void shouldMapProductDtoToResourceAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        ProductDto dto = new ProductDto(1L, "PROD1", "Product", "Description", 9.99, BigDecimal.TEN, CategoryEnum.TECHNOLOGIES, now, now);
        // Act
        ProductResource resource = ProductMapper.toResource(dto);
        ProductDto mappedDto = ProductMapper.toDto(resource);
        // Assert
        assertEquals(dto.getId(), resource.getId());
        assertEquals(dto.getProductCode(), resource.getProductCode());
        assertEquals(dto.getCreationDate(), resource.getCreationDate());
        assertEquals(dto.getId(), mappedDto.getId());
        assertEquals(dto.getProductCode(), mappedDto.getProductCode());
        assertEquals(dto.getCreationDate(), mappedDto.getCreationDate());
    }

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

