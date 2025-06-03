package it.marketplace.microservices.controller.order;

import it.marketplace.microservices.common.resource.OrderResource;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.OrderMapper;
import it.marketplace.microservices.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PutOrderControllerTest {

    @Mock
    private OrderService service;

    @InjectMocks
    private PutOrderController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateOrder_WhenValidResource_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        OrderResource resource = new OrderResource();
        doNothing().when(service).update(OrderMapper.toDto(resource));

        // Act
        ResponseEntity<Map<String, String>> response = controller.update(resource);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order Updated!", response.getBody().get("message"));
        verify(service).update(OrderMapper.toDto(resource));
    }

    @Test
    void shouldCancelOrder_WhenValidOrderCode_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        String code = "ORD123";
        doNothing().when(service).cancel(code);

        // Act
        ResponseEntity<Map<String, String>> response = controller.cancel(code);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order Cancelled!", response.getBody().get("message"));
        verify(service).cancel(code);
    }
}

