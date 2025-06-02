package it.marketplace.microservices.database.repository;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.database.entity.PaymentInstallmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentInstallmentsRepository extends JpaRepository<PaymentInstallmentsEntity, Long> {

    List<PaymentInstallmentsEntity> findByOrderCode(String orderCode);

    List<PaymentInstallmentsEntity> findByOrderCodeAndStatus(String orderCode, StatusOrderEnum statusOrderEnum);

}
