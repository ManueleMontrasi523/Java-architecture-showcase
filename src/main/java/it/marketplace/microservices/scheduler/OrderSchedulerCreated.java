package it.marketplace.microservices.scheduler;

import it.marketplace.microservices.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderSchedulerCreated {

    @Autowired
    private TransactionService service;

    // 1 MIN
    @Scheduled(fixedDelay = 60000)
    public void startProcessOrder() {
        service.readPendingPaymentsOrder();
        service.startAlignmentStatesOrder();
    }
}

