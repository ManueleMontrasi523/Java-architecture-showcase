package it.marketplace.microservices.service;

import java.util.Map;

public interface TransactionService {

    void startProcessing(String orderCode);

    void startPendingPayment(Map<String, String> orderCode);

    void readPendingPaymentsOrder();

    void startAlignmentStatesOrder();
}
