package it.marketplace.microservices.controller.order;

import it.marketplace.microservices.common.dto.OrderDto;
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

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PostOrderControllerTest {

    @Mock
    private OrderService service;

    @InjectMocks
    private PostOrderController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddOrder_WhenValidResource_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        OrderResource resource = new OrderResource();
        OrderDto dto = OrderMapper.toDto(resource);
        doNothing().when(service).save(dto);

        // Act
        ResponseEntity<Map<String, String>> response = controller.save(resource);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order added!", response.getBody().get("message"));
        verify(service).save(dto);
    }

    @Test
    void shouldAddAllOrders_WhenValidResources_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        OrderResource resource = new OrderResource();
        List<OrderResource> resources = List.of(resource);
        List<OrderDto> dtos = resources.stream().map(OrderMapper::toDto).toList();
        doNothing().when(service).saveAll(dtos);

        // Act
        ResponseEntity<?> response = controller.saveAll(resources);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Orders added!", ((Map<?, ?>) response.getBody()).get("message"));
        verify(service).saveAll(dtos);
    }
}

