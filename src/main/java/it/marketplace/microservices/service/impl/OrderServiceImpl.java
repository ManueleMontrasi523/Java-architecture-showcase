package it.marketplace.microservices.service.impl;

import it.marketplace.microservices.common.dto.OrderDto;
import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.OrderMapper;
import it.marketplace.microservices.database.entity.OrderEntity;
import it.marketplace.microservices.database.entity.UserEntity;
import it.marketplace.microservices.database.repository.OrderRepository;
import it.marketplace.microservices.service.OrderService;
import it.marketplace.microservices.service.ProductService;
import it.marketplace.microservices.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static it.marketplace.microservices.config.exception.ServiceException.ErrorCode.*;
import static it.marketplace.microservices.config.mapper.OrderMapper.toDto;
import static it.marketplace.microservices.config.mapper.OrderMapper.toEntity;
import static it.marketplace.microservices.utils.CopyProperties.copyNonNullProperties;
import static it.marketplace.microservices.utils.OrderGenerator.generateOrderCode;
import static java.util.Objects.isNull;

@Service
class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional
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
    }

    @Override
    public OrderDto findByCode(String code) throws ServiceException {
        OrderEntity entity = checkIfOrderExist(code);
        return toDto(entity);
    }

    @Override
    public List<OrderDto> findAll() {
        List<OrderEntity> entities = repository.findAll();
        return new ArrayList<>(entities.stream()
                .map(OrderMapper::toDto)
                .toList());
    }

    @Override
    public void update(OrderDto dto) throws ServiceException {
        OrderEntity entity = checkIfOrderExist(dto.getOrderCode());

        copyNonNullProperties(dto, entity);
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public void deleteByCode(String code) throws ServiceException {
        try {
            OrderEntity entity = checkIfOrderExist(code);
            repository.deleteById(entity.getId());
        } catch (ServiceException e) {
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    @Override
    public void startProcessing() {
        List<OrderEntity> orders = repository.findByStatus(StatusOrderEnum.CREATED);
        logger.info("Found {} orders in status CREATED", orders.size());
        if (!CollectionUtils.isEmpty(orders)) {
            orders.forEach(order -> {
                order.setStatus(StatusOrderEnum.PROCESSING);
                order.setTmsUpdate(LocalDateTime.now());
            });
            repository.saveAll(orders);
            logger.info("Update status in PROCESSING");
        }
    }

    @Override
    public void cancel(String code) {
        OrderEntity entity = checkIfOrderExist(code);
        entity.setStatus(StatusOrderEnum.CANCELLED);
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
    }

    private OrderEntity checkIfOrderExist(String code) throws ServiceException {
        OrderEntity entity = repository.findByOrderCodeIgnoreCase(code);
        if (isNull(entity))
            throw new ServiceException(ORDER_NOT_FOUND, "Order with code: " + code + " not found");
        return entity;
    }

    private void checkOrderOpenByUser(String email) throws ServiceException {
        if (repository.findOrderByUserMailAndStatus(email, StatusOrderEnum.CREATED) != null)
            throw new ServiceException(ORDER_EXIST_FOR_USER_FOUND, email + "Can't create another list! Please wait.");
    }
}
