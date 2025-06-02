package it.marketplace.microservices.service;

import it.marketplace.microservices.common.dto.OrderDto;
import it.marketplace.microservices.config.exception.ServiceException;

import java.util.List;

public interface OrderService {

    void save(OrderDto dto) throws ServiceException;

    void saveDirectly(OrderDto dto) throws ServiceException;

    void saveAll(List<OrderDto> dtos);

    OrderDto findByCode(String code) throws ServiceException;

    List<OrderDto> findByUserMail(String email) throws ServiceException;

    List<OrderDto> findAll() throws ServiceException;

    void update(OrderDto dto) throws ServiceException;

    void deleteByCode(String code) throws ServiceException;

    void cancel(String code);

    void payOrder(String orderCode);
}
