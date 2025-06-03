package it.marketplace.paymentInstallments;

import it.marketplace.common.exception.ServiceException;
import it.marketplace.service.PaymentInstallmentsService;
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

class PutPaymentInstallmentsControllerTest {

    @Mock
    private PaymentInstallmentsService service;

    @InjectMocks
    private PutPaymentInstallmentsController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldPayInstallment_WhenValidOrderCodeAndNumber_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        String orderCode = "ORD123";
        int number = 1;
        doNothing().when(service).payInstallments(orderCode, number);

        // Act
        ResponseEntity<Map<String, String>> response = controller.pay(orderCode, number);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Rate paid!", response.getBody().get("message"));
        verify(service).payInstallments(orderCode, number);
    }
}

