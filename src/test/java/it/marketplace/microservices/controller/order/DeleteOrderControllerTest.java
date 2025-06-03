package it.marketplace.microservices.controller.order;

import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.service.OrderService;
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

class DeleteOrderControllerTest {

    @Mock
    private OrderService service;

    @InjectMocks
    private DeleteOrderController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteOrder_WhenValidOrderCode_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        String orderCode = "ORD123";
        doNothing().when(service).deleteByCode(orderCode);

        // Act
        ResponseEntity<Map<String, String>> response = controller.delete(orderCode);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order deleted!", response.getBody().get("message"));
        verify(service).deleteByCode(orderCode);
    }
}

