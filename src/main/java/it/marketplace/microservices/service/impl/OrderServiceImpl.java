package it.marketplace.microservices.service.impl;

import it.marketplace.microservices.common.dto.OrderDto;
import it.marketplace.microservices.common.dto.UserDto;
import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.OrderMapper;
import it.marketplace.microservices.database.entity.OrderEntity;
import it.marketplace.microservices.database.entity.UserEntity;
import it.marketplace.microservices.database.repository.OrderRepository;
import it.marketplace.microservices.rabbitmq.RabbitMqProducer;
import it.marketplace.microservices.service.OrderService;
import it.marketplace.microservices.service.UserService;
import jakarta.persistence.criteria.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static it.marketplace.microservices.config.exception.ServiceException.ErrorCode.*;
import static it.marketplace.microservices.config.mapper.OrderMapper.toDto;
import static it.marketplace.microservices.config.mapper.OrderMapper.toEntity;
import static it.marketplace.microservices.utils.CopyProperties.copyNonNullProperties;
import static it.marketplace.microservices.utils.OrderGenerator.generateOrderCode;
import static java.util.Objects.isNull;

/**
 * Service implementation for managing orders in the marketplace system.
 * Handles order creation, update, deletion, payment, and related business logic.
 */
@Service
class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitMqProducer producer;

    /**
     * Saves a new order and sends a notification message.
     * @param dto the order DTO to save
     * @throws ServiceException if an order already exists for the user or another error occurs
     */
    @Override
    public void save(OrderDto dto) throws ServiceException {
        checkOrderOpenByUser(dto.getUser().getEmail());
        OrderEntity entity = toEntity(dto);

        UserEntity managedUser = userService.findByEmailEntity(dto.getUser().getEmail());
        entity.setUser(managedUser);

        String orderCode = generateOrderCode();
        LocalDateTime now = LocalDateTime.now();

        entity.setOrderCode(orderCode);
        entity.setOrderDate(now);
        entity.setTmsUpdate(now);

        entity.getProductOrder().forEach(product -> {
            product.setOrderCode(entity.getOrderCode());
            product.setCreationDate(now);
            product.setTmsUpdate(now);
        });

        repository.save(entity);
        producer.sendMessageNewOrder(orderCode);
    }

    /**
     * Saves an order directly without additional processing.
     * @param dto the order DTO to save
     * @throws ServiceException if an error occurs
     */
    @Override
    public void saveDirectly(OrderDto dto) throws ServiceException {
        repository.save(toEntity(dto));
    }

    /**
     * Saves a list of orders.
     * @param dtos the list of order DTOs to save
     * @throws ServiceException if an order already exists for a user or another error occurs
     */
    @Override
    public void saveAll(List<OrderDto> dtos) {
        try {
            LocalDateTime now = LocalDateTime.now();
            List<String> emails = dtos.stream().map(OrderDto::getUser).map(UserDto::getEmail).toList();
            emails.forEach(this::checkOrderOpenByUser);

            List<OrderEntity> entities = dtos.stream().map(OrderMapper::toEntity).toList();

            entities.forEach(entity -> {

                entity.setOrderCode(generateOrderCode());
                entity.setOrderDate(now);
                entity.setTmsUpdate(now);

                entity.getProductOrder().forEach(product -> {
                    product.setOrderCode(entity.getOrderCode());
                    product.setCreationDate(now);
                    product.setTmsUpdate(now);
                });
            });

            repository.saveAll(entities);
        } catch (ServiceException e) {
            logger.error("ERROR in the class {} with error {}", this.getClass().getName(), e.fillInStackTrace());
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    /**
     * Finds an order by its code.
     * @param code the order code
     * @return the matching OrderDto
     * @throws ServiceException if the order is not found
     */
    @Override
    public OrderDto findByCode(String code) throws ServiceException {
        OrderEntity entity = checkIfOrderExist(code);
        return toDto(entity);
    }

    /**
     * Finds all orders for a user by email.
     * @param email the user's email
     * @return a list of OrderDto
     * @throws ServiceException if an error occurs
     */
    @Override
    public List<OrderDto> findByUserMail(String email) throws ServiceException {
        return repository.findOrderByUserMail(email).stream().map(OrderMapper::toDto).toList();
    }

    /**
     * Finds all orders in the system.
     * @return a list of OrderDto
     */
    @Override
    public List<OrderDto> findAll() {
        List<OrderEntity> entities = repository.findAll();
        return new ArrayList<>(entities.stream()
                .map(OrderMapper::toDto)
                .toList());
    }

    /**
     * Updates an existing order.
     * @param dto the order DTO with updated data
     * @throws ServiceException if the order is not found or another error occurs
     */
    @Override
    public void update(OrderDto dto) throws ServiceException {
        OrderEntity entity = checkIfOrderExist(dto.getOrderCode());

        copyNonNullProperties(dto, entity);
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
    }

    /**
     * Deletes an order by its code.
     * @param code the order code to delete
     * @throws ServiceException if the order is not found or another error occurs
     */
    @Override
    public void deleteByCode(String code) throws ServiceException {
        try {
            OrderEntity entity = checkIfOrderExist(code);
            repository.deleteById(entity.getId());
        } catch (ServiceException e) {
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    /**
     * Cancels an order by its code.
     * @param code the order code to cancel
     */
    @Override
    public void cancel(String code) {
        OrderEntity entity = checkIfOrderExist(code);
        entity.setStatus(StatusOrderEnum.CANCELLED);
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
    }

    /**
     * Marks an order as paid by its code.
     * @param orderCode the order code to mark as paid
     */
    @Override
    public void payOrder(String orderCode) {
        OrderEntity entity = repository.findByOrderCodeIgnoreCase(orderCode);
        entity.setStatus(StatusOrderEnum.PAID);
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
    }

    /**
     * Checks if an order exists by code, throws exception if not found.
     * @param code the order code
     * @return the matching OrderEntity
     * @throws ServiceException if the order is not found
     */
    private OrderEntity checkIfOrderExist(String code) throws ServiceException {
        OrderEntity entity = repository.findByOrderCodeIgnoreCase(code);
        if (isNull(entity))
            throw new ServiceException(ORDER_NOT_FOUND, "Order with code: " + code + " not found");
        return entity;
    }

    /**
     * Checks if a user already has an open order, throws exception if so.
     * @param email the user's email
     * @throws ServiceException if an open order exists for the user
     */
    private void checkOrderOpenByUser(String email) throws ServiceException {
        if (repository.findOrderByUserMailAndStatus(email, StatusOrderEnum.CREATED) != null)
            throw new ServiceException(ORDER_EXIST_FOR_USER_FOUND, email + "Can't create another list! Please wait.");
    }
}
