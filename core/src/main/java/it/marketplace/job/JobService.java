package it.marketplace.job;

import it.marketplace.common.dto.OrderDto;
import it.marketplace.common.dto.ProductDto;
import it.marketplace.common.dto.ProductOrderDto;
import it.marketplace.rabbitmq.RabbitMqProducer;
import it.marketplace.service.OrderService;
import it.marketplace.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.MapUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.marketplace.common.enums.StatusOrderEnum.FAILED;
import static it.marketplace.common.enums.StatusOrderEnum.REJECTED;

/**
 * Service class for background job processing related to orders in the marketplace system.
 * Handles order validation, product supply checks, updates, and notifies via RabbitMQ.
 */
@Service
public class JobService {

    private static final Logger log = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private RabbitMqProducer producer;

    @Autowired
    @Lazy
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    /**
     * Starts asynchronous processing for a given order code.
     * Validates product supply, updates product and order status, and sends notifications.
     *
     * @param orderCode the code of the order to process
     */
    @Async
    public void startProcessing(String orderCode) {
        OrderDto orderDto = orderService.findByCode(orderCode);
        log.info("Start job for {}, find entity: {}", orderCode, orderDto);
        try {
            Thread.sleep(5000);
            Map<String, String> rejectReason = new HashMap<>();
            double debit = 0.0;
            List<ProductDto> toUpdate = new ArrayList<>();

            for (ProductOrderDto productOrderDto : orderDto.getProductOrder()) {
                ProductDto productDto = productService.findByCode(productOrderDto.getProductCode());

                BigDecimal supply = productDto.getSupply();
                BigDecimal order = productOrderDto.getQuantity();

                if (supply.compareTo(order) < 0) {
                    rejectReason.put(productOrderDto.getProductCode(), ("Exceed limit of " + supply + " requested: " + order));
                    // TODO if rejectReason present i can "stop process" and notify the user of this error
                } else {
                    productDto.setSupply(supply.subtract(order));
                    toUpdate.add(productDto);
                }
                debit = debit + productOrderDto.getTotal();
            }

            log.info("Check reason for reject order {} - {}", orderCode, rejectReason);

            if (!MapUtils.isEmpty(rejectReason)) {
                orderDto.setRejectReason(rejectReason.toString());
                orderDto.setStatus(REJECTED);
            }

            productService.saveAllDirectly(toUpdate);
            orderService.saveDirectly(orderDto);

            Map<String, String> message = new HashMap<>();
            message.put("orderCode", orderCode);
            message.put("debit", String.valueOf(debit));

            if (MapUtils.isEmpty(rejectReason)) producer.sendMessagePendingPayment(message);

        } catch (Exception ex) {
            log.info("Error during the jobService for order {} with error {}", orderCode, ex.fillInStackTrace());
            orderDto.setStatus(FAILED);
        }
    }
}
