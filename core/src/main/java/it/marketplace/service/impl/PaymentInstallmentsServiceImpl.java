package it.marketplace.service.impl;

import it.marketplace.common.dto.PaymentInstallmentsDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.repository.PaymentInstallmentsRepository;
import it.marketplace.service.PaymentInstallmentsService;
import it.marketplace.service.PaymentOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static it.marketplace.common.exception.ServiceException.ErrorCode.PAYMENT_RATE_EXCEED;

/**
 * Service implementation for managing payment installments in the marketplace system.
 * Handles retrieval and payment of order installments, including status updates and validation.
 */
@Service
public class PaymentInstallmentsServiceImpl implements PaymentInstallmentsService {

    private static final Logger log = LoggerFactory.getLogger(PaymentInstallmentsServiceImpl.class);

    @Autowired
    private PaymentInstallmentsRepository repository;
    @Autowired
    private PaymentOrderService service;

    /**
     * Retrieves all payment installments for a given order code.
     *
     * @param orderCode the order code
     *
     * @return a list of PaymentInstallmentsDto
     */
    @Override
    public List<PaymentInstallmentsDto> findAllByCode(String orderCode) {
        return repository.findByOrderCode(orderCode);
    }

    /**
     * Pays a specified number of installments for an order, updating their status and the payment order if all are paid.
     *
     * @param orderCode the order code
     * @param number    the number of installments to pay
     *
     * @throws ServiceException if the number exceeds available installments
     */
    @Override
    public void payInstallments(String orderCode, int number) {
        List<PaymentInstallmentsDto> dtos = repository.findByOrderCodeAndStatus(orderCode, StatusOrderEnum.PENDING_PAYMENT);
        boolean isAllPaid = dtos.size() == number;

        boolean isMajor = number > dtos.size();
        if (isMajor)
            throw new ServiceException(PAYMENT_RATE_EXCEED, "You only have " + dtos.size() + " installments left to pay, no more");

        List<PaymentInstallmentsDto> toUpdate = isAllPaid ? dtos
                : dtos.stream()
                .limit(number)
                .collect(Collectors.toList());

        for (PaymentInstallmentsDto entity : toUpdate) {
            entity.setStatus(StatusOrderEnum.PAID);
            entity.setTmsUpdate(LocalDateTime.now());
        }

        repository.saveAll(toUpdate);
        if (isAllPaid) service.payOrder(orderCode, false);
    }
}
