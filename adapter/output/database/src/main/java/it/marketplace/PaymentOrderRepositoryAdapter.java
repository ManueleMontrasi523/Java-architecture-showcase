package it.marketplace;

import it.marketplace.common.dto.PaymentOrderDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.mapper.PaymentOrderMapper;
import it.marketplace.repository.PaymentOrderJpaRepository;
import it.marketplace.repository.PaymentOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static it.marketplace.mapper.PaymentOrderMapper.toDto;
import static it.marketplace.mapper.PaymentOrderMapper.toEntity;

/**
 * Repository interface for managing PaymentOrderDto persistence operations.
 * Provides methods to find payment orders by code, status, and custom queries.
 */
@Repository
public class PaymentOrderRepositoryAdapter implements PaymentOrderRepository {

    @Autowired
    private PaymentOrderJpaRepository repository;

    /**
     * Saves a payment order in the database.
     *
     * @param dto the payment order to save
     */
    @Override
    public void save(PaymentOrderDto dto) {
        repository.save(toEntity(dto));
    }

    /**
     * Saves a list of payment orders in the database.
     *
     * @param toUpdate list of PaymentOrderDto to save
     */
    @Override
    public void saveAll(List<PaymentOrderDto> toUpdate) {
        repository.saveAll(toUpdate.stream().map(PaymentOrderMapper::toEntity).toList());
    }

    /**
     * Returns all payment orders present in the database.
     *
     * @return list of PaymentOrderDto
     */
    @Override
    public List<PaymentOrderDto> findAll() {
        return repository.findAll().stream().map(PaymentOrderMapper::toDto).toList();
    }

    /**
     * Finds a payment order by its code, ignoring case.
     *
     * @param orderCode the order code
     *
     * @return the matching PaymentOrderDto, or null if not found
     */
    @Override
    public PaymentOrderDto findByOrderCodeIgnoreCase(String orderCode) {
        return toDto(repository.findByOrderCodeIgnoreCase(orderCode));
    }

    /**
     * Finds all payment orders by a list of order codes.
     *
     * @param orderCodes the list of order codes
     *
     * @return a list of PaymentOrderDto
     */
    @Override
    public List<PaymentOrderDto> findByOrderCodeIn(List<String> orderCodes) {
        return repository.findByOrderCodeIn(orderCodes).stream()
                .map(PaymentOrderMapper::toDto)
                .toList();
    }

    /**
     * Finds all payment orders by status.
     *
     * @param status the payment order status
     *
     * @return a list of PaymentOrderDto
     */
    @Override
    public List<PaymentOrderDto> findByStatus(StatusOrderEnum status) {
        return repository.findByStatus(status).stream()
                .map(PaymentOrderMapper::toDto)
                .toList();
    }

    /**
     * Finds a payment order by code where the status is not the given status.
     *
     * @param orderCode the order code
     * @param status    the status to exclude
     *
     * @return the matching PaymentOrderDto, or null if not found
     */
    @Override
    public PaymentOrderDto findByOrderCodeAndStatus(String orderCode, StatusOrderEnum status) {
        return toDto(repository.findByOrderCodeAndStatus(orderCode, status));
    }
}
