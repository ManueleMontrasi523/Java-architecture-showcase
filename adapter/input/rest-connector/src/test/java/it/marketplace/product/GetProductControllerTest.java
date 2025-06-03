package it.marketplace.product;

import it.marketplace.common.dto.ProductDto;
import it.marketplace.common.mapper.ProductMapper;
import it.marketplace.common.resource.ProductResource;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class GetProductControllerTest {

    @Mock
    private ProductService service;

    @InjectMocks
    private GetProductController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnProduct_WhenFindByCode_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        String code = "PROD123";
        ProductDto dto = new ProductDto();
        ProductResource expected = ProductMapper.toResource(dto);
        when(service.findByCode(code)).thenReturn(dto);

        // Act
        ResponseEntity<ProductResource> response = controller.find(code);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(service).findByCode(code);
    }

    @Test
    void shouldReturnAllProducts_WhenFindAll_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        ProductDto dto = new ProductDto();
        List<ProductDto> dtos = List.of(dto);
        when(service.findAll()).thenReturn(dtos);
        List<ProductResource> expected = dtos.stream().map(ProductMapper::toResource).toList();

        // Act
        ResponseEntity<List<ProductResource>> response = controller.findAll();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(service).findAll();
    }
}

