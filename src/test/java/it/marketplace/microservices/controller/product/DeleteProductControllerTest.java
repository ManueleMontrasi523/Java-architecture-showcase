package it.marketplace.microservices.controller.product;

import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class DeleteProductControllerTest {

    @Mock
    private ProductService service;

    @InjectMocks
    private DeleteProductController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteProduct_WhenValidProductCode_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        String productCode = "PROD123";
        doNothing().when(service).deleteByCode(productCode);

        // Act
        ResponseEntity<Map<String, String>> response = controller.delete(productCode);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product deleted!", response.getBody().get("message"));
        verify(service).deleteByCode(productCode);
    }
}

