package it.marketplace.microservices.service.impl;

import it.marketplace.microservices.common.dto.OrderDto;
import it.marketplace.microservices.common.dto.PaymentOrderDto;
import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.config.mapper.PaymentOrderMapper;
import it.marketplace.microservices.database.entity.PaymentInstallmentsEntity;
import it.marketplace.microservices.database.entity.PaymentOrderEntity;
import it.marketplace.microservices.database.repository.PaymentInstallmentsRepository;
import it.marketplace.microservices.database.repository.PaymentOrderRepository;
import it.marketplace.microservices.service.OrderService;
import it.marketplace.microservices.service.PaymentOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static it.marketplace.microservices.common.enums.StatusOrderEnum.PENDING_PAYMENT;
import static java.util.Objects.nonNull;

@Service
class PaymentOrderServiceImpl implements PaymentOrderService {

    private static final Logger log = LoggerFactory.getLogger(PaymentOrderServiceImpl.class);

    @Autowired
    private PaymentOrderRepository repository;
    @Autowired
    private PaymentInstallmentsRepository paymentInstallmentsRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public List<PaymentOrderDto> findOrderByEmail(String email) {
        List<PaymentOrderDto> dtos = new ArrayList<>();
        List<OrderDto> orders = orderService.findByUserMail(email);
        List<String> orderCodes = !CollectionUtils.isEmpty(orders) ? orders.stream().map(OrderDto::getOrderCode).toList() : null;

        if (nonNull(orderCodes)) {
            dtos = repository.findByOrderCodeIn(orderCodes).stream().map(PaymentOrderMapper::toDto).toList();
        }
        return dtos;
    }

    @Override
    public List<PaymentOrderDto> findAll() {
        return repository.findAll().stream().map(PaymentOrderMapper::toDto).toList();
    }

    @Override
    public void payOrder(String orderCode, Boolean isInstallments) {
        log.info("Paid order {} with installments {}", orderCode, isInstallments);
        PaymentOrderEntity entity = repository.findByOrderCodeIgnoreCase(orderCode);
        if (Boolean.TRUE.equals(isInstallments)) {
            entity.setStatus(StatusOrderEnum.RATEIZED);
            payWithInstallments(orderCode, entity.getDebit());
        } else {
            entity.setStatus(StatusOrderEnum.PAID);
            orderService.payOrder(orderCode);
        }
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
    }

    private void payWithInstallments(String orderCode, Double debit) {
        List<PaymentInstallmentsEntity> payEntities = new ArrayList<>();
        double miniDebit = debit / 12;

        for (int i = 1; i <= 12; i++) {
            PaymentInstallmentsEntity installment = new PaymentInstallmentsEntity();
            installment.setReference("RATE_" + i);
            installment.setOrderCode(orderCode);
            installment.setStatus(PENDING_PAYMENT);
            installment.setDebit(miniDebit);
            installment.setTmsUpdate(LocalDateTime.now());
            payEntities.add(installment);
        }
        paymentInstallmentsRepository.saveAll(payEntities);
    }
}
