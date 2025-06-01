package it.marketplace.microservices.rabbitmq;

import it.marketplace.microservices.config.rabbitmq.RabbitMqConfig;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitMqProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessageNewOrder(String message) {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.IT_EXCHANGE,
                RabbitMqConfig.ROUTING_KEY,
                message
        );
        System.out.println("Message sendMessageNewOrder: " + message);
    }
}