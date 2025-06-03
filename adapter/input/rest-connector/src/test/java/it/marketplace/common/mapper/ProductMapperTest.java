package it.marketplace.common.mapper;

import it.marketplace.common.dto.ProductDto;
import it.marketplace.common.enums.CategoryEnum;
import it.marketplace.common.resource.ProductResource;
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
}

