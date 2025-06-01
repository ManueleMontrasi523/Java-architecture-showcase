package it.marketplace.microservices.service.impl;

import it.marketplace.microservices.common.dto.ProductDto;
import it.marketplace.microservices.common.dto.ProductOrderDto;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.ProductMapper;
import it.marketplace.microservices.database.entity.ProductEntity;
import it.marketplace.microservices.database.entity.ProductOrderEntity;
import it.marketplace.microservices.database.repository.ProductRepository;
import it.marketplace.microservices.service.ProductService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static it.marketplace.microservices.config.exception.ServiceException.ErrorCode.*;
import static it.marketplace.microservices.config.mapper.ProductMapper.toDto;
import static it.marketplace.microservices.config.mapper.ProductMapper.toEntity;
import static it.marketplace.microservices.utils.CopyProperties.copyNonNullProperties;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    @Autowired
    private ProductRepository repository;

    @Override
    public void save(ProductDto dto) throws ServiceException {
        try {
            ProductEntity entityOld = repository.findByProductCodeIgnoreCase(dto.getProductCode());
            if (nonNull(entityOld))
                throw new ServiceException(DATA_ALREADY_PRESENT, "Product already registered");

            ProductEntity entity = toEntity(dto);

            entity.setCreationDate(nonNull(dto.getCreationDate()) ? dto.getCreationDate() : LocalDateTime.now());
            entity.setTmsUpdate(LocalDateTime.now());

            repository.save(entity);
        } catch (ServiceException e) {
            logger.error("ERROR in the class {} with error {}", this.getClass().getName(), e.fillInStackTrace());
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    @Override
    public ProductDto findByCode(String code) throws ServiceException {
        ProductEntity entity = checkIfProductExist(code);
        return toDto(entity);
    }

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> dtos;
        List<ProductEntity> entities = repository.findAll();
        dtos = new ArrayList<>(entities.stream()
                .map(ProductMapper::toDto)
                .toList());
        return dtos;
    }

    @Override
    public void update(ProductDto dto) throws ServiceException {
        ProductEntity entity = checkIfProductExist(dto.getProductCode());

        copyNonNullProperties(dto, entity);
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public void deleteByCode(String code) throws ServiceException {
        try {
            ProductEntity entity = checkIfProductExist(code);
            repository.deleteById(entity.getId());
        } catch (ServiceException e) {
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateProductStorageStatus(List<ProductOrderEntity> productOrderEntity) {
        List<String> productCode = productOrderEntity.stream().map(ProductOrderEntity::getProductCode).toList();
        List<ProductEntity> entities = repository.findAllByProductCodeIn(productCode);
        LocalDateTime now = LocalDateTime.now();

        Map<String, ProductOrderEntity> orderMap = productOrderEntity.stream()
                .collect(Collectors.toMap(ProductOrderEntity::getProductCode, Function.identity()));

        entities.forEach(entity -> {
            ProductOrderEntity order = orderMap.get(entity.getProductCode());
            if (nonNull(order)) {
                entity.setSupply(entity.getSupply().subtract(order.getQuantity()));
                entity.setTmsUpdate(now);
            }
        });
    }

    @Override
    public List<String> checkRemainingSupplyProduct(List<ProductOrderDto> productOrderEntity) {
        List<String> productCode = productOrderEntity.stream().map(ProductOrderDto::getProductCode).toList();
        List<ProductEntity> entities = repository.findAllByProductCodeIn(productCode);

        Map<String, ProductOrderDto> orderMap = productOrderEntity.stream()
                .collect(Collectors.toMap(ProductOrderDto::getProductCode, Function.identity()));

        return entities.stream()
                .filter(entity -> {
                    ProductOrderDto order = orderMap.get(entity.getProductCode());
                    return order.getQuantity().compareTo(entity.getSupply()) > 0;
                })
                .map(ProductEntity::getProductCode)
                .toList();
    }

    private ProductEntity checkIfProductExist(String code) throws ServiceException {
        ProductEntity entity = repository.findByProductCodeIgnoreCase(code);
        if (isNull(entity))
            throw new ServiceException(PRODUCT_NOT_FOUND, "Product with code: " + code + " not found");
        return entity;
    }

}
