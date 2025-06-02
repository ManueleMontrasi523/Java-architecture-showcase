package it.marketplace.microservices.service.impl;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.database.entity.OrderEntity;
import it.marketplace.microservices.database.entity.PaymentOrderEntity;
import it.marketplace.microservices.database.repository.OrderRepository;
import it.marketplace.microservices.database.repository.PaymentOrderRepository;
import it.marketplace.microservices.job.JobService;
import it.marketplace.microservices.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.marketplace.microservices.common.enums.StatusOrderEnum.CANCELLED;
import static it.marketplace.microservices.common.enums.StatusOrderEnum.PENDING_PAYMENT;
import static java.util.Objects.nonNull;

@Service
class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    @Autowired
    private JobService job;


    @Override
    public void startProcessing(String orderCode) {
        logger.info("Arrived new order with code {} in status CREATED", orderCode);
        OrderEntity entity = orderRepository.findByOrderCodeIgnoreCase(orderCode);
        if (nonNull(entity)) {
            entity.setStatus(StatusOrderEnum.PROCESSING);
            entity.setTmsUpdate(LocalDateTime.now());
            orderRepository.save(entity);
            logger.info("Update status in PROCESSING for code {}", orderCode);

            job.startProcessing(orderCode);
        }
    }

    @Override
    public void startPendingPayment(Map<String, String> message) {
        logger.info("Start process pending payment with code {}", message);
        OrderEntity entity = orderRepository.findByOrderCodeIgnoreCase(message.get("orderCode"));
        if (nonNull(entity) && StatusOrderEnum.PROCESSING.equals(entity.getStatus())) {
            LocalDateTime now = LocalDateTime.now();
            entity.setStatus(PENDING_PAYMENT);
            entity.setTmsUpdate(now);

            PaymentOrderEntity paymentOrderEntity = new PaymentOrderEntity();
            paymentOrderEntity.setOrderCode(message.get("orderCode"));
            paymentOrderEntity.setStatus(PENDING_PAYMENT);
            paymentOrderEntity.setDebit(Double.valueOf(message.get("debit")));
            paymentOrderEntity.setOrderDate(now);
            paymentOrderEntity.setTmsUpdate(now);

            paymentOrderRepository.save(paymentOrderEntity);
            orderRepository.save(entity);
        }
    }

    @Override
    public void readPendingPaymentsOrder() {
        long orderProcessed = 0;
        logger.info("Reading order in status PENDING_PAYMENT...");
        List<OrderEntity> orderEntities = new ArrayList<>();
        List<PaymentOrderEntity> paymentOrderEntities = paymentOrderRepository.findByStatus(PENDING_PAYMENT);

        if (!CollectionUtils.isEmpty(paymentOrderEntities)) {
            for (PaymentOrderEntity paymentOrderEntity : paymentOrderEntities) {
                OrderEntity orderEntity = orderRepository.findByOrderCodeIgnoreCase(paymentOrderEntity.getOrderCode());
                if (StatusOrderEnum.PAID.equals(paymentOrderEntity.getStatus())) {
                    orderEntity.setStatus(StatusOrderEnum.PAID);
                    orderEntity.setTmsUpdate(LocalDateTime.now());
                    orderEntities.add(orderEntity);
                    orderProcessed++;
                }
            }
            if (!CollectionUtils.isEmpty(orderEntities)) orderRepository.saveAll(orderEntities);
            logger.info("Processed and PAID {} order", orderProcessed);
        } else {
            logger.info("No order found with status PENDING_PAYMENT...");
        }
    }

    @Override
    public void startAlignmentStatesOrder() {
        logger.info("Searching for orders with misaligned states CANCELLED...");
        long orderAligned = 0;
        List<PaymentOrderEntity> toUpdate = new ArrayList<>();
        List<OrderEntity> orderEntities = orderRepository.findByStatus(CANCELLED); // TODO - New logic to be studied as same records may be extracted
        List<String> orderCodes = orderEntities.stream().map(OrderEntity::getOrderCode).toList();

        if (!CollectionUtils.isEmpty(orderEntities)) {
            orderCodes.forEach(order -> {
                PaymentOrderEntity entity = paymentOrderRepository.findByOrderCodeAndStatus(order, CANCELLED);
                if (nonNull(entity)) {
                    entity.setStatus(CANCELLED);
                    entity.setTmsUpdate(LocalDateTime.now());
                    toUpdate.add(entity);
                }
            });
            logger.info("Aligned state in CANCELLED {} order", orderAligned);
        } else {
            logger.info("No order found with status misaligned...");
        }
        if (!CollectionUtils.isEmpty(toUpdate)) paymentOrderRepository.saveAll(toUpdate);
    }

}
