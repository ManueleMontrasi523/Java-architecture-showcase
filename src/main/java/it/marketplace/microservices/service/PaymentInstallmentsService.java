package it.marketplace.microservices.service;

import it.marketplace.microservices.common.dto.PaymentInstallmentsDto;

import java.util.List;

public interface PaymentInstallmentsService {

    List<PaymentInstallmentsDto> findAllByCode(String orderCode);

    void payInstallments(String orderCode, int number);
}
