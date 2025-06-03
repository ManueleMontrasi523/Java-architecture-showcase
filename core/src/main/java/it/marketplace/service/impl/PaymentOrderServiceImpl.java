package it.marketplace.service.impl;

import it.marketplace.common.dto.OrderDto;
import it.marketplace.common.dto.PaymentInstallmentsDto;
import it.marketplace.common.dto.PaymentOrderDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.repository.PaymentInstallmentsRepository;
import it.marketplace.repository.PaymentOrderRepository;
import it.marketplace.service.OrderService;
import it.marketplace.service.PaymentOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static it.marketplace.common.enums.StatusOrderEnum.PENDING_PAYMENT;
import static java.util.Objects.nonNull;

/**
 * Service implementation for managing payment orders in the marketplace system.
 * Handles retrieval, payment, and installment creation for payment orders.
 */
@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {

    private static final Logger log = LoggerFactory.getLogger(PaymentOrderServiceImpl.class);

    @Autowired
    private PaymentOrderRepository repository;
    @Autowired
    private PaymentInstallmentsRepository paymentInstallmentsRepository;

    @Autowired
    private OrderService orderService;

    /**
     * Retrieves all payment orders for a given user email.
     *
     * @param email the user email
     *
     * @return a list of PaymentOrderDto
     */
    @Override
    public List<PaymentOrderDto> findOrderByEmail(String email) {
        List<PaymentOrderDto> dtos = new ArrayList<>();
        List<OrderDto> orders = orderService.findByUserMail(email);
        List<String> orderCodes = !CollectionUtils.isEmpty(orders) ? orders.stream().map(OrderDto::getOrderCode).toList() : null;

        if (nonNull(orderCodes)) {
            dtos = repository.findByOrderCodeIn(orderCodes);
        }
        return dtos;
    }

    /**
     * Retrieves all payment orders in the system.
     *
     * @return a list of PaymentOrderDto
     */
    @Override
    public List<PaymentOrderDto> findAll() {
        return repository.findAll();
    }

    /**
     * Pays a payment order, updating its status and creating installments if needed.
     *
     * @param orderCode      the order code to pay
     * @param isInstallments whether the payment is in installments
     */
    @Override
    public void payOrder(String orderCode, Boolean isInstallments) {
        log.info("Paid order {} with installments {}", orderCode, isInstallments);
        PaymentOrderDto dto = repository.findByOrderCodeIgnoreCase(orderCode);
        if (Boolean.TRUE.equals(isInstallments)) {
            dto.setStatus(StatusOrderEnum.RATEIZED);
            payWithInstallments(orderCode, dto.getDebit());
        } else {
            dto.setStatus(StatusOrderEnum.PAID);
            orderService.payOrder(orderCode);
        }
        dto.setTmsUpdate(LocalDateTime.now());
        repository.save(dto);
    }

    /**
     * Creates 12 payment installments for an order, dividing the debit equally.
     *
     * @param orderCode the order code
     * @param debit     the total debit to divide
     */
    private void payWithInstallments(String orderCode, Double debit) {
        List<PaymentInstallmentsDto> payDtos = new ArrayList<>();
        double miniDebit = debit / 12;

        for (int i = 1; i <= 12; i++) {
            PaymentInstallmentsDto installment = new PaymentInstallmentsDto();
            installment.setReference("RATE_" + i);
            installment.setOrderCode(orderCode);
            installment.setStatus(PENDING_PAYMENT);
            installment.setDebit(miniDebit);
            installment.setTmsUpdate(LocalDateTime.now());
            payDtos.add(installment);
        }
        paymentInstallmentsRepository.saveAll(payDtos);
    }
}
