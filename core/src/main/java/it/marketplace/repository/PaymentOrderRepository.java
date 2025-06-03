package it.marketplace.repository;

import it.marketplace.common.dto.PaymentOrderDto;
import it.marketplace.common.enums.StatusOrderEnum;

import java.util.List;

public interface PaymentOrderRepository {

    void save(PaymentOrderDto dto);

    void saveAll(List<PaymentOrderDto> toUpdate);

    List<PaymentOrderDto> findAll();

    /**
     * Finds a payment order by its code, ignoring case.
     *
     * @param orderCode the order code
     *
     * @return the matching PaymentOrderDto, or null if not found
     */
    PaymentOrderDto findByOrderCodeIgnoreCase(String orderCode);

    /**
     * Finds all payment orders by a list of order codes.
     *
     * @param orderCodes the list of order codes
     *
     * @return a list of PaymentOrderDto
     */
    List<PaymentOrderDto> findByOrderCodeIn(List<String> orderCodes);

    /**
     * Finds all payment orders by status.
     *
     * @param status the payment order status
     *
     * @return a list of PaymentOrderDto
     */
    List<PaymentOrderDto> findByStatus(StatusOrderEnum status);

    /**
     * Finds a payment order by code where the status is not the given status.
     *
     * @param orderCode the order code
     * @param status    the status to exclude
     *
     * @return the matching PaymentOrderDto, or null if not found
     */
    PaymentOrderDto findByOrderCodeAndStatus(String orderCode, StatusOrderEnum status);

}
