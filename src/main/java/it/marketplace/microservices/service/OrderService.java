package it.marketplace.microservices.service;

import it.marketplace.microservices.common.dto.OrderDto;
import it.marketplace.microservices.config.exception.ServiceException;

import java.util.List;

/**
 * Service interface for managing orders in the marketplace system.
 * Provides methods for order creation, update, deletion, payment, and retrieval operations.
 */
public interface OrderService {

    /**
     * Saves a new order.
     * @param dto the order DTO to save
     * @throws ServiceException if an error occurs
     */
    void save(OrderDto dto) throws ServiceException;

    /**
     * Saves an order directly without additional processing.
     * @param dto the order DTO to save
     * @throws ServiceException if an error occurs
     */
    void saveDirectly(OrderDto dto) throws ServiceException;

    /**
     * Saves a list of orders.
     * @param dtos the list of order DTOs to save
     */
    void saveAll(List<OrderDto> dtos);

    /**
     * Finds an order by its code.
     * @param code the order code
     * @return the matching OrderDto
     * @throws ServiceException if the order is not found
     */
    OrderDto findByCode(String code) throws ServiceException;

    /**
     * Finds all orders for a user by email.
     * @param email the user's email
     * @return a list of OrderDto
     * @throws ServiceException if an error occurs
     */
    List<OrderDto> findByUserMail(String email) throws ServiceException;

    /**
     * Finds all orders in the system.
     * @return a list of OrderDto
     * @throws ServiceException if an error occurs
     */
    List<OrderDto> findAll() throws ServiceException;

    /**
     * Updates an existing order.
     * @param dto the order DTO with updated data
     * @throws ServiceException if the order is not found or another error occurs
     */
    void update(OrderDto dto) throws ServiceException;

    /**
     * Deletes an order by its code.
     * @param code the order code to delete
     * @throws ServiceException if the order is not found or another error occurs
     */
    void deleteByCode(String code) throws ServiceException;

    /**
     * Cancels an order by its code.
     * @param code the order code to cancel
     */
    void cancel(String code);

    /**
     * Marks an order as paid by its code.
     * @param orderCode the order code to mark as paid
     */
    void payOrder(String orderCode);
}
