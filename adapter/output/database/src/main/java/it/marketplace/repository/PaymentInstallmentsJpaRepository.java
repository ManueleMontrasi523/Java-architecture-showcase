package it.marketplace.repository;

import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.entity.PaymentInstallmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing PaymentInstallmentsEntity persistence operations.
 * Provides methods to find payment installments by order code and status.
 */
public interface PaymentInstallmentsJpaRepository extends JpaRepository<PaymentInstallmentsEntity, Long> {
    /**
     * Finds all payment installments by order code.
     *
     * @param orderCode the order code
     *
     * @return a list of PaymentInstallmentsEntity
     */
    List<PaymentInstallmentsEntity> findByOrderCode(String orderCode);

    /**
     * Finds all payment installments by order code and status.
     *
     * @param orderCode       the order code
     * @param statusOrderEnum the status of the payment installment
     *
     * @return a list of PaymentInstallmentsEntity
     */
    List<PaymentInstallmentsEntity> findByOrderCodeAndStatus(String orderCode, StatusOrderEnum statusOrderEnum);
}
