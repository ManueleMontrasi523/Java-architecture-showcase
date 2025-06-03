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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyList;

class GetOrderControllerTest {

    @Mock
    private OrderService service;

    @InjectMocks
    private GetOrderController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnOrder_WhenFindByCode_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        String code = "ORD123";
        OrderDto dto = new OrderDto();
        OrderResource resource = OrderMapper.toResource(dto);
        when(service.findByCode(code)).thenReturn(dto);

        // Act
        ResponseEntity<OrderResource> response = controller.find(code);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(resource, response.getBody());
        verify(service).findByCode(code);
    }

    @Test
    void shouldReturnAllOrders_WhenFindAll_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        OrderDto dto = new OrderDto();
        List<OrderDto> dtos = List.of(dto);
        when(service.findAll()).thenReturn(dtos);

        // Act
        ResponseEntity<List<OrderResource>> response = controller.findAll();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(service).findAll();
    }
}

