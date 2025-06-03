package it.marketplace.service.impl;

import it.marketplace.common.dto.OrderDto;
import it.marketplace.common.dto.UserDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.repository.OrderRepository;
import it.marketplace.service.OrderService;
import it.marketplace.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static it.marketplace.common.exception.ServiceException.ErrorCode.*;
import static it.marketplace.utils.CopyProperties.copyNonNullProperties;
import static it.marketplace.utils.OrderGenerator.generateOrderCode;
import static java.util.Objects.isNull;

/**
 * Service implementation for managing orders in the marketplace system.
 * Handles order creation, update, deletion, payment, and related business logic.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    /**
     * Saves a new order and sends a notification message.
     *
     * @param dto the order DTO to save
     *
     * @return
     *
     * @throws ServiceException if an order already exists for the user or another error occurs
     */
    @Override
    public String save(OrderDto dto) throws ServiceException {
        checkOrderOpenByUser(dto.getUser().getEmail());

        String orderCode = generateOrderCode();
        LocalDateTime now = LocalDateTime.now();

        dto.setOrderCode(orderCode);
        dto.setOrderDate(now);
        dto.setTmsUpdate(now);

        dto.getProductOrder().forEach(product -> {
            product.setOrderCode(orderCode);
            product.setCreationDate(now);
            product.setTmsUpdate(now);
        });

        repository.save(dto);
        return orderCode;
    }

    /**
     * Saves an order directly without additional processing.
     *
     * @param dto the order DTO to save
     *
     * @throws ServiceException if an error occurs
     */
    @Override
    public void saveDirectly(OrderDto dto) throws ServiceException {
        repository.save(dto);
    }

    /**
     * Saves a list of orders.
     *
     * @param dtos the list of order DTOs to save
     *
     * @throws ServiceException if an order already exists for a user or another error occurs
     */
    @Override
    public void saveAll(List<OrderDto> dtos) {
        try {
            LocalDateTime now = LocalDateTime.now();
            List<String> emails = dtos.stream().map(OrderDto::getUser).map(UserDto::getEmail).toList();
            emails.forEach(this::checkOrderOpenByUser);

            dtos.forEach(dto -> {

                dto.setOrderCode(generateOrderCode());
                dto.setOrderDate(now);
                dto.setTmsUpdate(now);

                dto.getProductOrder().forEach(product -> {
                    product.setOrderCode(dto.getOrderCode());
                    product.setCreationDate(now);
                    product.setTmsUpdate(now);
                });
            });

            repository.saveAll(dtos);
        } catch (ServiceException e) {
            logger.error("ERROR in the class {} with error {}", this.getClass().getName(), e.fillInStackTrace());
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    /**
     * Finds an order by its code.
     *
     * @param code the order code
     *
     * @return the matching OrderDto
     *
     * @throws ServiceException if the order is not found
     */
    @Override
    public OrderDto findByCode(String code) throws ServiceException {
        return checkIfOrderExist(code);
    }

    /**
     * Finds all orders for a user by email.
     *
     * @param email the user's email
     *
     * @return a list of OrderDto
     *
     * @throws ServiceException if an error occurs
     */
    @Override
    public List<OrderDto> findByUserMail(String email) throws ServiceException {
        return repository.findOrderByUserMail(email);
    }

    /**
     * Finds all orders in the system.
     *
     * @return a list of OrderDto
     */
    @Override
    public List<OrderDto> findAll() {
        return repository.findAll();
    }

    /**
     * Updates an existing order.
     *
     * @param dto the order DTO with updated data
     *
     * @throws ServiceException if the order is not found or another error occurs
     */
    @Override
    public void update(OrderDto dto) throws ServiceException {
        OrderDto dtoOld = checkIfOrderExist(dto.getOrderCode());

        copyNonNullProperties(dtoOld, dto);
        dto.setTmsUpdate(LocalDateTime.now());
        repository.save(dto);
    }

    /**
     * Deletes an order by its code.
     *
     * @param code the order code to delete
     *
     * @throws ServiceException if the order is not found or another error occurs
     */
    @Override
    public void deleteByCode(String code) throws ServiceException {
        try {
            OrderDto dto = checkIfOrderExist(code);
            repository.deleteById(dto.getId());
        } catch (ServiceException e) {
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    /**
     * Cancels an order by its code.
     *
     * @param code the order code to cancel
     */
    @Override
    public void cancel(String code) {
        OrderDto dto = checkIfOrderExist(code);
        dto.setStatus(StatusOrderEnum.CANCELLED);
        dto.setTmsUpdate(LocalDateTime.now());
        repository.save(dto);
    }

    /**
     * Marks an order as paid by its code.
     *
     * @param orderCode the order code to mark as paid
     */
    @Override
    public void payOrder(String orderCode) {
        OrderDto dto = repository.findByOrderCodeIgnoreCase(orderCode);
        dto.setStatus(StatusOrderEnum.PAID);
        dto.setTmsUpdate(LocalDateTime.now());
        repository.save(dto);
    }

    /**
     * Checks if an order exists by code, throws exception if not found.
     *
     * @param code the order code
     *
     * @return the matching OrderEntity
     *
     * @throws ServiceException if the order is not found
     */
    private OrderDto checkIfOrderExist(String code) throws ServiceException {
        OrderDto dto = repository.findByOrderCodeIgnoreCase(code);
        if (isNull(dto))
            throw new ServiceException(ORDER_NOT_FOUND, "Order with code: " + code + " not found");
        return dto;
    }

    /**
     * Checks if a user already has an open order, throws exception if so.
     *
     * @param email the user's email
     *
     * @throws ServiceException if an open order exists for the user
     */
    private void checkOrderOpenByUser(String email) throws ServiceException {
        if (repository.findOrderByUserMailAndStatus(email, StatusOrderEnum.CREATED) != null)
            throw new ServiceException(ORDER_EXIST_FOR_USER_FOUND, email + "Can't create another list! Please wait.");
    }
}
