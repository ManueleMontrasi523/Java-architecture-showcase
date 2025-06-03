package it.marketplace.repository;

import it.marketplace.common.dto.ProductDto;
import it.marketplace.common.enums.CategoryEnum;
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
        ProductDto entity = new ProductDto();
        entity.setProductCode("PROD1");
        entity.setName("TEST");
        entity.setPRICE(0.59);
        entity.setSupply(BigDecimal.valueOf(500));
        entity.setCategory(CategoryEnum.TECHNOLOGIES);
        entity.setCreationDate(LocalDateTime.now());
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
        // Act
        ProductDto found = repository.findByProductCodeIgnoreCase("prod1");
        // Assert
        assertNotNull(found);
        assertEquals("PROD1", found.getProductCode());
    }

    @Test
    void shouldFindAllByProductCodeIn_ArrangeActAssert() {
        // Arrange
        ProductDto entity = new ProductDto();
        entity.setProductCode("PROD2");
        entity.setName("TEST");
        entity.setPRICE(0.59);
        entity.setSupply(BigDecimal.valueOf(500));
        entity.setCategory(CategoryEnum.TECHNOLOGIES);
        entity.setCreationDate(LocalDateTime.now());
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
        // Act
        List<ProductDto> found = repository.findAllByProductCodeIn(List.of("PROD2"));
        // Assert
        assertFalse(found.isEmpty());
        assertEquals("PROD2", found.get(0).getProductCode());
    }
}

