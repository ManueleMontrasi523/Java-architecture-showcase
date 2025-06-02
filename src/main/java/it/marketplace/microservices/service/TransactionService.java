package it.marketplace.microservices.service;

import java.util.Map;

/**
 * Service interface for managing transactions in the marketplace system.
 * Provides methods for processing orders, handling pending payments, and aligning order/payment states.
 */
public interface TransactionService {

    /**
     * Starts processing for a new order, updating its status and triggering background jobs.
     * @param orderCode the order code to process
     */
    void startProcessing(String orderCode);

    /**
     * Starts processing for a pending payment, updating order and payment order status.
     * @param orderCode the message map containing order code and debit
     */
    void startPendingPayment(Map<String, String> orderCode);

    /**
     * Reads all orders in PENDING_PAYMENT status and updates their state if paid.
     */
    void readPendingPaymentsOrder();

    /**
     * Aligns the states of orders and payment orders marked as CANCELLED.
     */
    void startAlignmentStatesOrder();
}
