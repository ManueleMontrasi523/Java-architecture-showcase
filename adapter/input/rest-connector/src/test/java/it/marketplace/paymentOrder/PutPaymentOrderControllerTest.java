package it.marketplace.paymentOrder;

import it.marketplace.common.exception.ServiceException;
import it.marketplace.service.PaymentOrderService;
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

class PutPaymentOrderControllerTest {

    @Mock
    private PaymentOrderService service;

    @InjectMocks
    private PutPaymentOrderController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldPayOrder_WhenValidOrderCodeAndInstallments_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        String orderCode = "ORD123";
        Boolean isInstallments = true;
        doNothing().when(service).payOrder(orderCode, isInstallments);

        // Act
        ResponseEntity<Map<String, String>> response = controller.pay(orderCode, isInstallments);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order paid!", response.getBody().get("message"));
        verify(service).payOrder(orderCode, isInstallments);
    }
}

