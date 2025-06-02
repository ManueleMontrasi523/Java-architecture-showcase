package it.marketplace.microservices.config.rabbitmq;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RabbitMqConfigTest {
    private final RabbitMqConfig config = new RabbitMqConfig();

    @Test
    void shouldCreateExchange() {
        // Act
        TopicExchange exchange = config.itExchange();
        // Assert
        assertEquals(RabbitMqConfig.IT_EXCHANGE, exchange.getName());
    }

    @Test
    void shouldCreateNotifyNewOrderQueue() {
        // Act
        Queue queue = config.notifyNewOrderQueue();
        // Assert
        assertEquals(RabbitMqConfig.NOTIFY_NEW_ORDER_QUEUE, queue.getName());
    }

    @Test
    void shouldCreateNotifyPendingPaymentQueue() {
        // Act
        Queue queue = config.notifyPendingPaymentQueue();
        // Assert
        assertEquals(RabbitMqConfig.NOTIFY_PENDING_PAYMENT_QUEUE, queue.getName());
    }

    @Test
    void shouldBindNotifyNewOrderQueue() {
        // Act
        Binding binding = config.bindingNotifyNewOrderRoutingKey();
        // Assert
        assertEquals(RabbitMqConfig.NEW_ORDER_ROUTING_KEY, binding.getRoutingKey());
    }

    @Test
    void shouldBindNotifyPendingPaymentQueue() {
        // Act
        Binding binding = config.bindingPendingPaymentRoutingKey();
        // Assert
        assertEquals(RabbitMqConfig.PENDING_PAYMENT_ROUTING_KEY, binding.getRoutingKey());
    }
}

