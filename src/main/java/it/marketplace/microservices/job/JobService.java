package it.marketplace.microservices.job;

import it.marketplace.microservices.common.dto.OrderDto;
import it.marketplace.microservices.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static it.marketplace.microservices.common.enums.StatusOrderEnum.FAILED;
import static it.marketplace.microservices.common.enums.StatusOrderEnum.PENDING_PAYMENT;

@Service
public class JobService {

    private static final Logger log = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private OrderService service;

    @Async
    public void processJobAsync(String orderCode) {
        OrderDto dto = service.findByCode(orderCode);
        try {
            Thread.sleep(5000);


            // TODO CONTROLLI
            dto.setStatus(PENDING_PAYMENT);
        } catch (Exception ex) {
            log.info("Error during the jobService for order {} with error {}", orderCode, ex.fillInStackTrace());
            dto.setStatus(FAILED);
        }
        service.save(dto);
    }
}
