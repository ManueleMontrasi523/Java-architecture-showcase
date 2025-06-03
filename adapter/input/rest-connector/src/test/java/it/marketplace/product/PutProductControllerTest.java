package it.marketplace.product;

import it.marketplace.common.mapper.ProductMapper;
import it.marketplace.common.resource.ProductResource;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.common.validation.ProductValidator;
import it.marketplace.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class PutProductControllerTest {

    @Mock
    private ProductService service;
    @Mock
    private ProductValidator validator;
    @Mock
    private WebDataBinder binder;

    @InjectMocks
    private PutProductController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller.initBinder(binder);
    }

    @Test
    void shouldUpdateProduct_WhenValidResource_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        ProductResource resource = new ProductResource();
        doNothing().when(service).update(ProductMapper.toDto(resource));

        // Act
        ResponseEntity<Map<String, String>> response = controller.update(resource);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product updated!", response.getBody().get("message"));
        verify(service).update(ProductMapper.toDto(resource));
    }
}

