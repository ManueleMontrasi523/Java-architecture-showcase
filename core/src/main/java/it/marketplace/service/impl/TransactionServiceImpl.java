package it.marketplace.service.impl;

import it.marketplace.common.dto.OrderDto;
import it.marketplace.common.dto.PaymentOrderDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.job.JobService;
import it.marketplace.repository.OrderRepository;
import it.marketplace.repository.PaymentOrderRepository;
import it.marketplace.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.marketplace.common.enums.StatusOrderEnum.CANCELLED;
import static it.marketplace.common.enums.StatusOrderEnum.PENDING_PAYMENT;
import static java.util.Objects.nonNull;

/**
 * Service implementation for managing transactions in the marketplace system.
 * Handles order processing, payment processing, and state alignment for orders and payments.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    @Autowired
    private JobService job;

    /**
     * Starts processing for a new order, updating its status and triggering background jobs.
     *
     * @param orderCode the order code to process
     */
    @Override
    public void startProcessing(String orderCode) {
        logger.info("Arrived new order with code {} in status CREATED", orderCode);
        OrderDto dto = orderRepository.findByOrderCodeIgnoreCase(orderCode);
        if (nonNull(dto)) {
            dto.setStatus(StatusOrderEnum.PROCESSING);
            dto.setTmsUpdate(LocalDateTime.now());
            orderRepository.save(dto);
            logger.info("Update status in PROCESSING for code {}", orderCode);

            job.startProcessing(orderCode);
        }
    }

    /**
     * Starts processing for a pending payment, updating order and payment order status.
     *
     * @param message the message map containing order code and debit
     */
    @Override
    public void startPendingPayment(Map<String, String> message) {
        logger.info("Start process pending payment with code {}", message);
        OrderDto dto = orderRepository.findByOrderCodeIgnoreCase(message.get("orderCode"));
        if (nonNull(dto) && StatusOrderEnum.PROCESSING.equals(dto.getStatus())) {
            LocalDateTime now = LocalDateTime.now();
            dto.setStatus(PENDING_PAYMENT);
            dto.setTmsUpdate(now);

            PaymentOrderDto paymentOrderDto = new PaymentOrderDto();
            paymentOrderDto.setOrderCode(message.get("orderCode"));
            paymentOrderDto.setStatus(PENDING_PAYMENT);
            paymentOrderDto.setDebit(Double.valueOf(message.get("debit")));
            paymentOrderDto.setOrderDate(now);
            paymentOrderDto.setTmsUpdate(now);

            paymentOrderRepository.save(paymentOrderDto);
            orderRepository.save(dto);
        }
    }

    /**
     * Reads all orders in PENDING_PAYMENT status and updates their state if paid.
     */
    @Override
    public void readPendingPaymentsOrder() {
        long orderProcessed = 0;
        logger.info("Reading order in status PENDING_PAYMENT...");
        List<OrderDto> orderEntities = new ArrayList<>();
        List<PaymentOrderDto> paymentOrderEntities = paymentOrderRepository.findByStatus(PENDING_PAYMENT);

        if (!CollectionUtils.isEmpty(paymentOrderEntities)) {
            for (PaymentOrderDto paymentOrderDto : paymentOrderEntities) {
                OrderDto orderDto = orderRepository.findByOrderCodeIgnoreCase(paymentOrderDto.getOrderCode());
                if (StatusOrderEnum.PAID.equals(paymentOrderDto.getStatus())) {
                    orderDto.setStatus(StatusOrderEnum.PAID);
                    orderDto.setTmsUpdate(LocalDateTime.now());
                    orderEntities.add(orderDto);
                    orderProcessed++;
                }
            }
            if (!CollectionUtils.isEmpty(orderEntities)) orderRepository.saveAll(orderEntities);
            logger.info("Processed and PAID {} order", orderProcessed);
        } else {
            logger.info("No order found with status PENDING_PAYMENT...");
        }
    }

    /**
     * Aligns the states of orders and payment orders marked as CANCELLED.
     */
    @Override
    public void startAlignmentStatesOrder() {
        logger.info("Searching for orders with misaligned states CANCELLED...");
        long orderAligned = 0;
        List<PaymentOrderDto> toUpdate = new ArrayList<>();
        List<OrderDto> orderEntities = orderRepository.findByStatus(CANCELLED); // TODO - New logic to be studied as same records may be extracted
        List<String> orderCodes = orderEntities.stream().map(OrderDto::getOrderCode).toList();

        if (!CollectionUtils.isEmpty(orderEntities)) {
            orderCodes.forEach(order -> {
                PaymentOrderDto entity = paymentOrderRepository.findByOrderCodeAndStatus(order, CANCELLED);
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


