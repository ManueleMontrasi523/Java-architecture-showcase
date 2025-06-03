package it.marketplace;

import it.marketplace.common.dto.OrderDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.entity.OrderEntity;
import it.marketplace.mapper.OrderMapper;
import it.marketplace.repository.OrderJpaRepository;
import it.marketplace.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static it.marketplace.mapper.OrderMapper.toDto;
import static it.marketplace.mapper.OrderMapper.toEntity;

/**
 * Repository interface for managing OrderEntity persistence operations.
 * Provides methods to find orders by code, user email, and status.
 */
@Repository
public class OrderRepositoryAdapter implements OrderRepository {

    @Autowired
    private OrderJpaRepository repository;

    @Override
    public void save(OrderDto dto) {
        repository.save(toEntity(dto));
    }

    @Override
    public void saveAll(List<OrderDto> dtos) {
        List<OrderEntity> entities = dtos.stream()
                .map(OrderMapper::toEntity)
                .toList();
        repository.saveAll(entities);
    }

    @Override
    public List<OrderDto> findAll() {
        return repository.findAll().stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    /**
     * Finds an order by its code, ignoring case.
     *
     * @param orderCode the order code
     *
     * @return the matching OrderEntity, or null if not found
     */
    @Override
    public OrderDto findByOrderCodeIgnoreCase(String orderCode) {
        return toDto(repository.findByOrderCodeIgnoreCase(orderCode));
    }

    /**
     * Finds an order by user email and status.
     *
     * @param email  the user's email
     * @param status the order status
     *
     * @return the matching OrderEntity, or null if not found
     */
    @Override
    public OrderDto findOrderByUserMailAndStatus(String email, StatusOrderEnum status) {
        return toDto(repository.findOrderByUserMailAndStatus(email, status));
    }

    /**
     * Finds all orders by user email.
     *
     * @param email the user's email
     *
     * @return a list of OrderEntity
     */
    @Override
    public List<OrderDto> findOrderByUserMail(String email) {
        return repository.findOrderByUserMail(email).stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    /**
     * Finds all orders by status.
     *
     * @param status the order status
     *
     * @return a list of OrderEntity
     */
    @Override
    public List<OrderDto> findByStatus(StatusOrderEnum status) {
        return repository.findByStatus(status).stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
