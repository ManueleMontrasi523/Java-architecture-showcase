package it.marketplace.microservices.controller.paymentInstallments;

import it.marketplace.microservices.common.dto.PaymentInstallmentsDto;
import it.marketplace.microservices.common.resource.PaymentInstallmentsResource;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.PaymentInstallmentsMapper;
import it.marketplace.microservices.service.PaymentInstallmentsService;
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

class GetPaymentInstallmentsControllerTest {

    @Mock
    private PaymentInstallmentsService service;

    @InjectMocks
    private GetPaymentInstallmentsController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnInstallments_WhenFindByOrderCode_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        String orderCode = "ORD123";
        PaymentInstallmentsDto dto = new PaymentInstallmentsDto();
        List<PaymentInstallmentsDto> dtos = List.of(dto);
        when(service.findAllByCode(orderCode)).thenReturn(dtos);
        List<PaymentInstallmentsResource> expected = dtos.stream().map(PaymentInstallmentsMapper::toResource).toList();

        // Act
        ResponseEntity<List<PaymentInstallmentsResource>> response = controller.find(orderCode);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(service).findAllByCode(orderCode);
    }
}

