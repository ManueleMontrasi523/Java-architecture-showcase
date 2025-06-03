package it.marketplace;

import it.marketplace.rabbitmq.RabbitMqConfig;
import it.marketplace.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * RabbitMQ listener component for processing order and payment messages in the marketplace system.
 * Handles new order and pending payment events from the message queues.
 */
@Component
public class MyRabbitListener {

    private static final Logger log = LoggerFactory.getLogger(MyRabbitListener.class);

    @Autowired
    private TransactionService service;

    /**
     * Handles messages for new orders from the queue.
     *
     * @param orderCode the order code received from the queue
     *
     * @throws InterruptedException if the thread is interrupted
     */
    @RabbitListener(queues = RabbitMqConfig.NOTIFY_NEW_ORDER_QUEUE)
    public void listenerNewOrder(String orderCode) throws InterruptedException {
        Thread.sleep(5000);
        log.info("Received message new order: {}", orderCode);
        service.startProcessing(orderCode);
    }

    /**
     * Handles messages for pending payments from the queue.
     *
     * @param message the message map received from the queue
     *
     * @throws InterruptedException if the thread is interrupted
     */
    @RabbitListener(queues = RabbitMqConfig.NOTIFY_PENDING_PAYMENT_QUEUE)
    public void listenerPendingPayment(Map<String, String> message) throws InterruptedException {
        Thread.sleep(5000);
        log.info("Received message pending payment: {}", message);
        service.startPendingPayment(message);
    }
}

