package it.marketplace.microservices.database.repository;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.database.entity.OrderEntity;
import it.marketplace.microservices.database.entity.PaymentOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrderEntity, Long> {

    PaymentOrderEntity findByOrderCodeIgnoreCase(String orderCode);

    List<PaymentOrderEntity> findByOrderCodeIn(List<String> orderCodes);

    List<PaymentOrderEntity> findByStatus(StatusOrderEnum status);

    @Query("SELECT poe FROM PaymentOrderEntity poe WHERE orderCode = :orderCode AND status <> :status")
    PaymentOrderEntity findByOrderCodeAndStatus(String orderCode, StatusOrderEnum status);
}
