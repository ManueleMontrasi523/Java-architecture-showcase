package it.marketplace.rabbitmq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;

class RabbitMqProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMqProducer producer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSendMessageNewOrder_WhenCalled_ThenArrangeActAssert() {
        // Arrange
        String message = "ORD123";
        // Act
        producer.sendMessageNewOrder(message);
        // Assert
        verify(rabbitTemplate).convertAndSend(
                RabbitMqConfig.IT_EXCHANGE,
                RabbitMqConfig.NEW_ORDER_ROUTING_KEY,
                message
        );
    }

    @Test
    void shouldSendMessagePendingPayment_WhenCalled_ThenArrangeActAssert() {
        // Arrange
        Map<String, Object> message = new HashMap<>();
        message.put("orderCode", "ORD123");
        // Act
        producer.sendMessagePendingPayment(message);
        // Assert
        verify(rabbitTemplate).convertAndSend(
                RabbitMqConfig.IT_EXCHANGE,
                RabbitMqConfig.PENDING_PAYMENT_ROUTING_KEY,
                message
        );
    }
}

