package it.marketplace.microservices.service;

import it.marketplace.microservices.common.dto.PaymentOrderDto;

import java.util.List;

public interface PaymentOrderService {

    List<PaymentOrderDto> findOrderByEmail(String email);

    List<PaymentOrderDto> findAll();

    void payOrder(String orderCode);
}
