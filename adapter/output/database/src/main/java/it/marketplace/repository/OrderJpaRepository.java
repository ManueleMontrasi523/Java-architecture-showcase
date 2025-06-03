package it.marketplace.repository;

import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing OrderEntity persistence operations.
 * Provides methods to find orders by code, user email, and status.
 */
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    /**
     * Finds an order by its code, ignoring case.
     *
     * @param orderCode the order code
     *
     * @return the matching OrderEntity, or null if not found
     */
    OrderEntity findByOrderCodeIgnoreCase(String orderCode);

    /**
     * Finds an order by user email and status.
     *
     * @param email  the user's email
     * @param status the order status
     *
     * @return the matching OrderEntity, or null if not found
     */
    @Query("SELECT oe FROM OrderEntity oe WHERE user.email = :email AND status = :status")
    OrderEntity findOrderByUserMailAndStatus(String email, StatusOrderEnum status);

    /**
     * Finds all orders by user email.
     *
     * @param email the user's email
     *
     * @return a list of OrderEntity
     */
    @Query("SELECT oe FROM OrderEntity oe WHERE user.email = :email")
    List<OrderEntity> findOrderByUserMail(String email);

    /**
     * Finds all orders by status.
     *
     * @param status the order status
     *
     * @return a list of OrderEntity
     */
    List<OrderEntity> findByStatus(StatusOrderEnum status);
}
