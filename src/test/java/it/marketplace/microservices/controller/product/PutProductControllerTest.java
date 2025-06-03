package it.marketplace.microservices.controller.product;

import it.marketplace.microservices.common.resource.ProductResource;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.ProductMapper;
import it.marketplace.microservices.service.ProductService;
import it.marketplace.microservices.config.validation.ProductValidator;
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

