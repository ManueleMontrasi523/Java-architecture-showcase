package it.marketplace.microservices.common.resource;

import it.marketplace.microservices.common.enums.CategoryEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductResourceTest {
    @Test
    void shouldCreateProductResourceWithAllFields() {
        // Arrange
        LocalDateTime creationDate = LocalDateTime.now();
        // Act
        ProductResource resource = new ProductResource(1L, "PROD1", "Product", "Description", 9.99, BigDecimal.TEN, CategoryEnum.TECHNOLOGIES, creationDate);
        // Assert
        assertEquals(1L, resource.getId());
        assertEquals("PROD1", resource.getProductCode());
        assertEquals("Product", resource.getName());
        assertEquals("Description", resource.getDescription());
        assertEquals(9.99, resource.getPrice());
        assertEquals(BigDecimal.TEN, resource.getSupply());
        assertEquals(CategoryEnum.TECHNOLOGIES, resource.getCategory());
        assertEquals(creationDate, resource.getCreationDate());
    }

    @Test
    void shouldSetAndGetIdAndCreationDate() {
        // Arrange
        ProductResource resource = new ProductResource();
        LocalDateTime creationDate = LocalDateTime.now();
        // Act
        resource.setId(55L);
        resource.setCreationDate(creationDate);
        // Assert
        assertEquals(55L, resource.getId());
        assertEquals(creationDate, resource.getCreationDate());
    }
}

