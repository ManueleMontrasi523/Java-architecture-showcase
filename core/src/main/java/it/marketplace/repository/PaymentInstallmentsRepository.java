package it.marketplace.repository;

import it.marketplace.common.dto.PaymentInstallmentsDto;
import it.marketplace.common.enums.StatusOrderEnum;

import java.util.List;


public interface PaymentInstallmentsRepository {

    void saveAll(List<PaymentInstallmentsDto> toUpdate);

    /**
     * Finds all payment installments by order code.
     *
     * @param orderCode the order code
     *
     * @return a list of PaymentInstallmentsDto
     */
    List<PaymentInstallmentsDto> findByOrderCode(String orderCode);

    /**
     * Finds all payment installments by order code and status.
     *
     * @param orderCode       the order code
     * @param statusOrderEnum the status of the payment installment
     *
     * @return a list of PaymentInstallmentsDto
     */
    List<PaymentInstallmentsDto> findByOrderCodeAndStatus(String orderCode, StatusOrderEnum statusOrderEnum);

}
