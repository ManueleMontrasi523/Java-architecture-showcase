package it.marketplace.microservices.service.impl;

import it.marketplace.microservices.common.dto.ProductDto;
import it.marketplace.microservices.common.exception.ProductServiceException;
import it.marketplace.microservices.common.mapper.ProductMapper;
import it.marketplace.microservices.database.entity.ProductEntity;
import it.marketplace.microservices.database.repository.ProductRepository;
import it.marketplace.microservices.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static it.marketplace.microservices.common.exception.ProductServiceException.ErrorCode.*;
import static it.marketplace.microservices.common.mapper.ProductMapper.toDto;
import static it.marketplace.microservices.common.mapper.ProductMapper.toEntity;
import static it.marketplace.microservices.utils.CopyProperties.copyNonNullProperties;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    @Autowired
    private ProductRepository repository;

    @Override
    public void save(ProductDto dto) throws ProductServiceException {
        try {
            ProductEntity entityOld = repository.findByProductCodeIgnoreCase(dto.getProductCode());
            if (nonNull(entityOld))
                throw new ProductServiceException(DATA_ALREADY_PRESENT, "Product already registered");

            ProductEntity entity = toEntity(dto);

            entity.setCreationDate(nonNull(dto.getCreationDate()) ? dto.getCreationDate() : LocalDateTime.now());
            entity.setTmsUpdate(LocalDateTime.now());

            repository.save(entity);
        } catch (ProductServiceException e) {
            logger.error("ERROR in the class {} with error {}", this.getClass().getName(), e.fillInStackTrace());
            throw new ProductServiceException(GENERIC_ERROR, e.getErrorMessage());
        }
    }

    @Override
    public ProductDto findByCode(String code) throws ProductServiceException {
        ProductEntity entity = checkIfProductExist(code);
        return toDto(entity);
    }

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> dtos;
        List<ProductEntity> entitys = repository.findAll();
        dtos = new ArrayList<>(entitys.stream()
                .map(ProductMapper::toDto)
                .toList());
        return dtos;
    }

    @Override
    public void update(ProductDto dto) throws ProductServiceException {
        ProductEntity entity = checkIfProductExist(dto.getProductCode());

        copyNonNullProperties(dto, entity);
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    public void deleteByCode(String code) throws ProductServiceException {
        try {
            ProductEntity entity = checkIfProductExist(code);
            repository.deleteById(entity.getId());
        } catch (ProductServiceException e) {
            throw new ProductServiceException(GENERIC_ERROR, e.getErrorMessage());
        }
    }

    private ProductEntity checkIfProductExist(String code) throws ProductServiceException {
        ProductEntity entity = repository.findByProductCodeIgnoreCase(code);
        if (isNull(entity))
            throw new ProductServiceException(PRODUCT_NOT_FOUND, "Product with code: " + code + " not found");
        return entity;
    }

}
