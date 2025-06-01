package it.marketplace.microservices.database.repository;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.database.entity.OrderEntity;
import it.marketplace.microservices.database.entity.PaymentOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrderEntity, Long> {

    PaymentOrderEntity findByOrderCodeIgnoreCase(String orderCode);

    List<PaymentOrderEntity> findByStatus(StatusOrderEnum status);

}
