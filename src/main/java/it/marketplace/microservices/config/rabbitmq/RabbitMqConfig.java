package it.marketplace.microservices.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ in the marketplace system.
 * Defines exchanges, queues, and bindings for order notifications.
 */
@Configuration
public class RabbitMqConfig {

    public static final String IT_EXCHANGE = "it.exchange";

    public static final String NOTIFY_NEW_ORDER_QUEUE = "notify-new-order-queue";
    public static final String NOTIFY_PENDING_PAYMENT_QUEUE = "notify-pending-payment-queue";

    public static final String NEW_ORDER_ROUTING_KEY = "notifyNewOrderRoutingKey";
    public static final String PENDING_PAYMENT_ROUTING_KEY = "notifyPendingPaymentRoutingKey";

    /**
     * Defines the topic exchange for the application.
     * @return the TopicExchange bean
     */
    @Bean
    TopicExchange itExchange() {
        return ExchangeBuilder.topicExchange(IT_EXCHANGE).durable(false).build();
    }

    /**
     * Defines the queue for new order notifications.
     * @return the Queue bean
     */
    @Bean
    Queue notifyNewOrderQueue() {
        return QueueBuilder.nonDurable(NOTIFY_NEW_ORDER_QUEUE).build();
    }

    /**
     * Defines the queue for pending payment notifications.
     * @return the Queue bean
     */
    @Bean
    Queue notifyPendingPaymentQueue() {
        return QueueBuilder.nonDurable(NOTIFY_PENDING_PAYMENT_QUEUE).build();
    }

    /**
     * Binds the new order queue to the exchange with the new order routing key.
     * @return the Binding bean
     */
    @Bean
    Binding bindingNotifyNewOrderRoutingKey() {
        return BindingBuilder
                .bind(notifyNewOrderQueue())
                .to(itExchange())
                .with(NEW_ORDER_ROUTING_KEY);
    }

    /**
     * Binds the pending payment queue to the exchange with the pending payment routing key.
     * @return the Binding bean
     */
    @Bean
    Binding bindingPendingPaymentRoutingKey() {
        return BindingBuilder
                .bind(notifyPendingPaymentQueue())
                .to(itExchange())
                .with(PENDING_PAYMENT_ROUTING_KEY);
    }
}
