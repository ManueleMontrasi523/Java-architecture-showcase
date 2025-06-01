package it.marketplace.microservices.scheduler;

import it.marketplace.microservices.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OrderSchedulerCreated {

    private static final Logger log = LoggerFactory.getLogger(OrderSchedulerCreated.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private OrderService service;

    // 1 MIN
    @Scheduled(fixedDelay = 60000)
    public void startProcessOrder() {
        log.info("The time is now {}", dateFormat.format(new Date()));
//        service.startProcessing();
    }
}

