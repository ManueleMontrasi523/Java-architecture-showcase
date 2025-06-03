package it.marketplace.repository;

import it.marketplace.common.dto.OrderDto;
import it.marketplace.common.enums.StatusOrderEnum;

import java.util.List;

public interface OrderRepository {

    void save(OrderDto dto);

    void saveAll(List<OrderDto> dtos);

    List<OrderDto> findAll();

    /**
     * Finds an order by its code, ignoring case.
     *
     * @param orderCode the order code
     *
     * @return the matching OrderDto, or null if not found
     */
    OrderDto findByOrderCodeIgnoreCase(String orderCode);

    /**
     * Finds an order by user email and status.
     *
     * @param email  the user's email
     * @param status the order status
     *
     * @return the matching OrderDto, or null if not found
     */
    OrderDto findOrderByUserMailAndStatus(String email, StatusOrderEnum status);

    /**
     * Finds all orders by user email.
     *
     * @param email the user's email
     *
     * @return a list of OrderDto
     */
    List<OrderDto> findOrderByUserMail(String email);

    /**
     * Finds all orders by status.
     *
     * @param status the order status
     *
     * @return a list of OrderDto
     */
    List<OrderDto> findByStatus(StatusOrderEnum status);

    void deleteById(Long id);

}
