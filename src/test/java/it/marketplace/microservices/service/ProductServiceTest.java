package it.marketplace.microservices.service;

import it.marketplace.microservices.common.dto.ProductDto;
import it.marketplace.microservices.database.entity.ProductEntity;
import it.marketplace.microservices.database.repository.ProductRepository;
import it.marketplace.microservices.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindByCode_WhenProductExists_ThenArrangeActAssert() {
        // Arrange
        String code = "PROD123";
        ProductEntity entity = new ProductEntity();
        when(repository.findByProductCodeIgnoreCase(code)).thenReturn(entity);
        // Act
        ProductDto result = service.findByCode(code);
        // Assert
        assertNotNull(result);
        verify(repository).findByProductCodeIgnoreCase(code);
    }
}

