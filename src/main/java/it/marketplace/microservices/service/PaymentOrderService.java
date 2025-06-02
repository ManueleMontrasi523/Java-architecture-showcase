package it.marketplace.microservices.service;

import it.marketplace.microservices.common.dto.PaymentOrderDto;

import java.util.List;

/**
 * Service interface for managing payment orders in the marketplace system.
 * Provides methods for retrieving, paying, and listing payment orders.
 */
public interface PaymentOrderService {

    /**
     * Retrieves all payment orders for a given user email.
     * @param email the user email
     * @return a list of PaymentOrderDto
     */
    List<PaymentOrderDto> findOrderByEmail(String email);

    /**
     * Retrieves all payment orders in the system.
     * @return a list of PaymentOrderDto
     */
    List<PaymentOrderDto> findAll();

    /**
     * Pays a payment order, updating its status and creating installments if needed.
     * @param orderCode the order code to pay
     * @param isInstallments whether the payment is in installments
     */
    void payOrder(String orderCode, Boolean isInstallments);
}
