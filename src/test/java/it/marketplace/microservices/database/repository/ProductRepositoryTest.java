package it.marketplace.microservices.database.repository;

import it.marketplace.microservices.common.enums.CategoryEnum;
import it.marketplace.microservices.database.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    void shouldFindByProductCodeIgnoreCase_ArrangeActAssert() {
        // Arrange
        ProductEntity entity = new ProductEntity();
        entity.setProductCode("PROD1");
        entity.setName("TEST");
        entity.setPRICE(0.59);
        entity.setSupply(BigDecimal.valueOf(500));
        entity.setCategory(CategoryEnum.TECHNOLOGIES);
        entity.setCreationDate(LocalDateTime.now());
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
        // Act
        ProductEntity found = repository.findByProductCodeIgnoreCase("prod1");
        // Assert
        assertNotNull(found);
        assertEquals("PROD1", found.getProductCode());
    }

    @Test
    void shouldFindAllByProductCodeIn_ArrangeActAssert() {
        // Arrange
        ProductEntity entity = new ProductEntity();
        entity.setProductCode("PROD2");
        entity.setName("TEST");
        entity.setPRICE(0.59);
        entity.setSupply(BigDecimal.valueOf(500));
        entity.setCategory(CategoryEnum.TECHNOLOGIES);
        entity.setCreationDate(LocalDateTime.now());
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
        // Act
        List<ProductEntity> found = repository.findAllByProductCodeIn(List.of("PROD2"));
        // Assert
        assertFalse(found.isEmpty());
        assertEquals("PROD2", found.get(0).getProductCode());
    }
}

