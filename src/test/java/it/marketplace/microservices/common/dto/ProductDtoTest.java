package it.marketplace.microservices.common.dto;

import it.marketplace.microservices.common.enums.CategoryEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductDtoTest {

    @Test
    void shouldCreateProductDtoWithAllFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        // Act
        ProductDto dto = new ProductDto(1L, "PROD1", "Product", "Description", 9.99, BigDecimal.TEN, CategoryEnum.TECHNOLOGIES, now, now);
        // Assert
        assertEquals("1", dto.getId().toString());
        assertEquals("PROD1", dto.getProductCode());
        assertEquals("Product", dto.getName());
        assertEquals(9.99, dto.getPRICE());
        assertEquals(BigDecimal.TEN, dto.getSupply());
        assertEquals(CategoryEnum.TECHNOLOGIES, dto.getCategory());
        assertEquals(now, dto.getCreationDate());
        assertEquals(now, dto.getTmsUpdate());
    }

    @Test
    void shouldSetAndGetDescription() {
        // Arrange
        ProductDto dto = new ProductDto();
        // Act
        dto.setDescription("New description");
        // Assert
        assertEquals("New description", dto.getDescription());
    }
}

