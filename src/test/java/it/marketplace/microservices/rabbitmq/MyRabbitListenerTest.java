package it.marketplace.microservices.rabbitmq;

import it.marketplace.microservices.config.rabbitmq.RabbitMqConfig;
import it.marketplace.microservices.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;

class MyRabbitListenerTest {

    @Mock
    private TransactionService service;

    @InjectMocks
    private MyRabbitListener listener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCallStartProcessing_WhenListenerNewOrder_ThenArrangeActAssert() throws Exception {
        // Arrange
        String orderCode = "ORD123";
        // Act
        listener.listenerNewOrder(orderCode);
        // Assert
        verify(service).startProcessing(orderCode);
    }

    @Test
    void shouldCallStartPendingPayment_WhenListenerPendingPayment_ThenArrangeActAssert() throws Exception {
        // Arrange
        Map<String, String> message = new HashMap<>();
        message.put("orderCode", "ORD123");
        // Act
        listener.listenerPendingPayment(message);
        // Assert
        verify(service).startPendingPayment(message);
    }
}

