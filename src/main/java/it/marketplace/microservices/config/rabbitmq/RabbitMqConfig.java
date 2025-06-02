package it.marketplace.microservices.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String IT_EXCHANGE = "it.exchange";

    public static final String NOTIFY_NEW_ORDER_QUEUE = "notify-new-order-queue";
    public static final String NOTIFY_PENDING_PAYMENT_QUEUE = "notify-pending-payment-queue";

    public static final String NEW_ORDER_ROUTING_KEY = "notifyNewOrderRoutingKey";
    public static final String PENDING_PAYMENT_ROUTING_KEY = "notifyPendingPaymentRoutingKey";

    @Bean
    TopicExchange itExchange() {
        return ExchangeBuilder.topicExchange(IT_EXCHANGE).durable(false).build();
    }



    @Bean
    Queue notifyNewOrderQueue() {
        return QueueBuilder.nonDurable(NOTIFY_NEW_ORDER_QUEUE).build();
    }

    @Bean
    Queue notifyPendingPaymentQueue() {
        return QueueBuilder.nonDurable(NOTIFY_PENDING_PAYMENT_QUEUE).build();
    }



    @Bean
    Binding bindingNotifyNewOrderRoutingKey() {
        return BindingBuilder
                .bind(notifyNewOrderQueue())
                .to(itExchange())
                .with(NEW_ORDER_ROUTING_KEY);
    }

    @Bean
    Binding bindingPendingPaymentRoutingKey() {
        return BindingBuilder
                .bind(notifyPendingPaymentQueue())
                .to(itExchange())
                .with(PENDING_PAYMENT_ROUTING_KEY);
    }
}
