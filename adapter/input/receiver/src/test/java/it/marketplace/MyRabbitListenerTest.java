package it.marketplace;

import it.marketplace.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class MyRabbitListenerTest {

    @Mock
    private TransactionService transactionService;

    private MyRabbitListener myRabbitListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        myRabbitListener = new MyRabbitListener();
        ReflectionTestUtils.setField(myRabbitListener, "service", transactionService);
    }

    @Test
    void shouldArrangeActAssert_listenerNewOrder() throws InterruptedException {
        // Arrange
        String orderCode = "ORD123";
        // Act
        myRabbitListener.listenerNewOrder(orderCode);
        // Assert
        verify(transactionService, times(1)).startProcessing(orderCode);
    }

    @Test
    void shouldArrangeActAssert_listenerPendingPayment() throws InterruptedException {
        // Arrange
        Map<String, String> message = new HashMap<>();
        message.put("order", "ORD456");
        // Act
        myRabbitListener.listenerPendingPayment(message);
        // Assert
        verify(transactionService, times(1)).startPendingPayment(message);
    }
}

