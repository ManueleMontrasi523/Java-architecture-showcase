package it.marketplace.microservices.controller.paymentOrder;

import it.marketplace.microservices.common.dto.PaymentOrderDto;
import it.marketplace.microservices.common.resource.PaymentOrderResource;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.PaymentOrderMapper;
import it.marketplace.microservices.service.PaymentOrderService;
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

class GetPaymentOrderControllerTest {

    @Mock
    private PaymentOrderService service;

    @InjectMocks
    private GetPaymentOrderController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnPaymentOrders_WhenFindByUserEmail_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        String email = "test@email.com";
        PaymentOrderDto dto = new PaymentOrderDto();
        List<PaymentOrderDto> dtos = List.of(dto);
        when(service.findOrderByEmail(email)).thenReturn(dtos);
        List<PaymentOrderResource> expected = dtos.stream().map(PaymentOrderMapper::toResource).toList();

        // Act
        ResponseEntity<List<PaymentOrderResource>> response = controller.find(email);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(service).findOrderByEmail(email);
    }

    @Test
    void shouldReturnAllPaymentOrders_WhenFindAll_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        PaymentOrderDto dto = new PaymentOrderDto();
        List<PaymentOrderDto> dtos = List.of(dto);
        when(service.findAll()).thenReturn(dtos);
        List<PaymentOrderResource> expected = dtos.stream().map(PaymentOrderMapper::toResource).toList();

        // Act
        ResponseEntity<List<PaymentOrderResource>> response = controller.findAll();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(service).findAll();
    }
}

