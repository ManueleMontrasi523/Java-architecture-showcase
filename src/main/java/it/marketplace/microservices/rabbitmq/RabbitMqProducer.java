package it.marketplace.microservices.rabbitmq;

import it.marketplace.microservices.config.rabbitmq.RabbitMqConfig;
import it.marketplace.microservices.scheduler.OrderSchedulerCreated;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * RabbitMQ producer component for sending order and payment messages in the marketplace system.
 * Provides methods to send new order and pending payment messages to the appropriate queues.
 */
@Component
@AllArgsConstructor
public class RabbitMqProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderSchedulerCreated.class);
    private final RabbitTemplate rabbitTemplate;

    /**
     * Sends a message for a new order to the RabbitMQ exchange.
     * @param message the order code message to send
     */
    public void sendMessageNewOrder(String message) {
        log.info("Produce message for sendMessageNewOrder: {}", message);
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.IT_EXCHANGE,
                RabbitMqConfig.NEW_ORDER_ROUTING_KEY,
                message
        );
    }

    /**
     * Sends a message for a pending payment to the RabbitMQ exchange.
     * @param message the message map to send
     */
    public void sendMessagePendingPayment(Map<String, ?> message) {
        log.info("Produce message for sendMessagePendingPayment: {}", message);
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.IT_EXCHANGE,
                RabbitMqConfig.PENDING_PAYMENT_ROUTING_KEY,
                message
        );
    }
}

