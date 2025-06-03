package it.marketplace.service;

import it.marketplace.common.dto.ProductDto;
import it.marketplace.repository.ProductRepository;
import it.marketplace.service.impl.ProductServiceImpl;
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
        ProductDto dto = new ProductDto();
        when(repository.findByProductCodeIgnoreCase(code)).thenReturn(dto);
        // Act
        ProductDto result = service.findByCode(code);
        // Assert
        assertNotNull(result);
        verify(repository).findByProductCodeIgnoreCase(code);
    }
}

