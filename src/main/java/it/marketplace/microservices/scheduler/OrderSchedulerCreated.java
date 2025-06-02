package it.marketplace.microservices.scheduler;

import it.marketplace.microservices.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler component for periodically processing and aligning order states in the marketplace system.
 * Triggers background jobs to read pending payments and align order statuses at a fixed interval.
 */
@Component
public class OrderSchedulerCreated {

    @Autowired
    private TransactionService service;

    // 1 MIN
    /**
     * Scheduled task that processes pending payments and aligns order states every minute.
     */
    @Scheduled(fixedDelay = 60000)
    public void startProcessOrder() {
        service.readPendingPaymentsOrder();
        service.startAlignmentStatesOrder();
    }
}

