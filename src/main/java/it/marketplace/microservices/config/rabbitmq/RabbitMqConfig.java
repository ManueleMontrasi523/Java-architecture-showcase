package it.marketplace.microservices.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String NOTIFY_NEW_ORDER_QUEUE = "notify-new-order-queue";
    public static final String IT_EXCHANGE = "it.exchange";
    public static final String ROUTING_KEY = "notifyNewOrderRoutingKey";

    @Bean
    Queue notifyNewOrderQueue() {
        return QueueBuilder.nonDurable(NOTIFY_NEW_ORDER_QUEUE).build();
    }

    @Bean
    TopicExchange itExchange() {
        return ExchangeBuilder.topicExchange(IT_EXCHANGE).durable(false).build();
    }

    @Bean
    Binding bindingNotifyNewOrderRoutingKey() {
        return BindingBuilder
                .bind(notifyNewOrderQueue())
                .to(itExchange())
                .with(ROUTING_KEY);
    }
}
