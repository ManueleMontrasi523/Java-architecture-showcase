package it.marketplace;

import it.marketplace.common.dto.PaymentInstallmentsDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.mapper.PaymentInstallmentsMapper;
import it.marketplace.repository.PaymentInstallmentsJpaRepository;
import it.marketplace.repository.PaymentInstallmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing PaymentInstallmentsEntity persistence operations.
 * Provides methods to find payment installments by order code and status.
 */
@Repository
public class PaymentInstallmentsRepositoryAdapter implements PaymentInstallmentsRepository {

    @Autowired
    private PaymentInstallmentsJpaRepository repository;

    /**
     * Saves a list of payment installments in the database.
     *
     * @param toUpdate list of PaymentInstallmentsDto to save
     */
    @Override
    public void saveAll(List<PaymentInstallmentsDto> toUpdate) {
        repository.saveAll(toUpdate.stream().map(PaymentInstallmentsMapper::toEntity).toList());
    }

    /**
     * Finds all payment installments by order code.
     *
     * @param orderCode the order code
     *
     * @return a list of PaymentInstallmentsEntity
     */
    @Override
    public List<PaymentInstallmentsDto> findByOrderCode(String orderCode) {
        return repository.findByOrderCode(orderCode).stream()
                .map(PaymentInstallmentsMapper::toDto)
                .toList();
    }

    /**
     * Finds all payment installments by order code and status.
     *
     * @param orderCode       the order code
     * @param statusOrderEnum the status of the payment installment
     *
     * @return a list of PaymentInstallmentsEntity
     */
    @Override
    public List<PaymentInstallmentsDto> findByOrderCodeAndStatus(String orderCode, StatusOrderEnum statusOrderEnum) {
        return repository.findByOrderCodeAndStatus(orderCode, statusOrderEnum).stream()
                .map(PaymentInstallmentsMapper::toDto)
                .toList();
    }
}
