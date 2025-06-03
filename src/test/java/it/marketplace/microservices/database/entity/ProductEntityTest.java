package it.marketplace.microservices.database.entity;

import it.marketplace.microservices.common.enums.CategoryEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityTest {

    @Test
    void shouldCreateEntityWithAllArgsConstructor_ArrangeActAssert() {
        // Arrange/Act
        LocalDateTime now = LocalDateTime.now();
        ProductEntity entity = new ProductEntity(1L, "PROD1", "Name", "Desc", 10.5, BigDecimal.ONE, CategoryEnum.TECHNOLOGIES, now, now);
        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("PROD1", entity.getProductCode());
        assertEquals("Name", entity.getName());
        assertEquals("Desc", entity.getDescription());
        assertEquals(10.5, entity.getPRICE());
        assertEquals(BigDecimal.ONE, entity.getSupply());
        assertEquals(CategoryEnum.TECHNOLOGIES, entity.getCategory());
        assertEquals(now, entity.getCreationDate());
        assertEquals(now, entity.getTmsUpdate());
    }

    @Test
    void shouldSetAndGetFields_ArrangeActAssert() {
        // Arrange
        ProductEntity entity = new ProductEntity();
        LocalDateTime now = LocalDateTime.now();
        // Act
        entity.setId(2L);
        entity.setProductCode("PROD2");
        entity.setName("N");
        entity.setDescription("D");
        entity.setPRICE(20.0);
        entity.setSupply(BigDecimal.TEN);
        entity.setCategory(CategoryEnum.HOME);
        entity.setCreationDate(now);
        entity.setTmsUpdate(now);
        // Assert
        assertEquals(2L, entity.getId());
        assertEquals("PROD2", entity.getProductCode());
        assertEquals("N", entity.getName());
        assertEquals("D", entity.getDescription());
        assertEquals(20.0, entity.getPRICE());
        assertEquals(BigDecimal.TEN, entity.getSupply());
        assertEquals(CategoryEnum.HOME, entity.getCategory());
        assertEquals(now, entity.getCreationDate());
        assertEquals(now, entity.getTmsUpdate());
    }

    @Test
    void shouldTestEqualsAndHashCode_ArrangeActAssert() {
        // Arrange
        ProductEntity e1 = new ProductEntity();
        ProductEntity e2 = new ProductEntity();
        // Act
        e1.setId(1L); e2.setId(1L);
        e1.setProductCode("P"); e2.setProductCode("P");
        // Assert
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void shouldTestToString_ArrangeActAssert() {
        // Arrange
        ProductEntity entity = new ProductEntity();
        entity.setProductCode("P");
        // Act
        String str = entity.toString();
        // Assert
        assertTrue(str.contains("P"));
    }
}

