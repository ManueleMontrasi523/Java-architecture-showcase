package it.marketplace.microservices.rabbitmq;

import it.marketplace.microservices.config.rabbitmq.RabbitMqConfig;
import it.marketplace.microservices.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRabbitListener {

    @Autowired
    private OrderService service;

    @RabbitListener(queues = RabbitMqConfig.NOTIFY_NEW_ORDER_QUEUE)
    public void listenerNewOrder(String orderCode) {
        System.out.println("Received message: " + orderCode);
        service.startProcessing(orderCode);
    }
}