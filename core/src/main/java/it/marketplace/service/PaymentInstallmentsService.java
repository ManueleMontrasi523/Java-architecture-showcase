package it.marketplace.service;

import it.marketplace.common.dto.PaymentInstallmentsDto;

import java.util.List;

/**
 * Service interface for managing payment installments in the marketplace system.
 * Provides methods for retrieving and paying order installments.
 */
public interface PaymentInstallmentsService {

    /**
     * Retrieves all payment installments for a given order code.
     * @param orderCode the order code
     * @return a list of PaymentInstallmentsDto
     */
    List<PaymentInstallmentsDto> findAllByCode(String orderCode);

    /**
     * Pays a specified number of installments for an order.
     * @param orderCode the order code
     * @param number the number of installments to pay
     */
    void payInstallments(String orderCode, int number);
}
