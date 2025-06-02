package it.marketplace.microservices.database.repository;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.database.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    OrderEntity findByOrderCodeIgnoreCase(String orderCode);

    @Query("SELECT oe FROM OrderEntity oe WHERE user.email = :email AND status = :status")
    OrderEntity findOrderByUserMailAndStatus(String email, StatusOrderEnum status);

    @Query("SELECT oe FROM OrderEntity oe WHERE user.email = :email")
    List<OrderEntity> findOrderByUserMail(String email);

    List<OrderEntity> findByStatus(StatusOrderEnum status);
}
