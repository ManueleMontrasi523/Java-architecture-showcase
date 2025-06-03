package it.marketplace.service.impl;

import it.marketplace.common.dto.ProductDto;
import it.marketplace.common.dto.ProductOrderDto;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.repository.ProductRepository;
import it.marketplace.service.ProductService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static it.marketplace.common.exception.ServiceException.ErrorCode.*;
import static it.marketplace.utils.CopyProperties.copyNonNullProperties;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Service implementation for managing products in the marketplace system.
 * Handles product creation, update, deletion, inventory management, and supply checks.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    @Autowired
    private ProductRepository repository;

    /**
     * Saves a new product.
     *
     * @param dto the product DTO to save
     *
     * @throws ServiceException if the product already exists or another error occurs
     */
    @Override
    public void save(ProductDto dto) throws ServiceException {
        try {
            LocalDateTime now = LocalDateTime.now();
            ProductDto dtoOld = repository.findByProductCodeIgnoreCase(dto.getProductCode());
            if (nonNull(dtoOld))
                throw new ServiceException(DATA_ALREADY_PRESENT, "Product already registered");

            dto.setCreationDate(now);
            dto.setTmsUpdate(now);

            repository.save(dto);
        } catch (ServiceException e) {
            logger.error("ERROR in the class {} with error {}", this.getClass().getName(), e.fillInStackTrace());
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    /**
     * Saves a list of products.
     *
     * @param dto the list of product DTOs to save
     *
     * @throws ServiceException if any product already exists or another error occurs
     */
    @Override
    public void saveAll(List<ProductDto> dto) {
        try {
            LocalDateTime now = LocalDateTime.now();
            List<String> productDtos = dto.stream().map(ProductDto::getProductCode).toList();
            List<ProductDto> dtoOld = repository.findAllByProductCodeIn(productDtos);
            if (!CollectionUtils.isEmpty(dtoOld))
                throw new ServiceException(DATA_ALREADY_PRESENT, "Products already registered");

            dto.forEach(d -> {
                d.setCreationDate(now);
                d.setTmsUpdate(now);
            });

            repository.saveAll(dto);
        } catch (ServiceException e) {
            logger.error("ERROR in the class {} with error {}", this.getClass().getName(), e.fillInStackTrace());
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    /**
     * Saves a list of products directly without additional processing.
     *
     * @param dto the list of product DTOs to save
     */
    @Override
    public void saveAllDirectly(List<ProductDto> dto) {
        repository.saveAll(dto);
    }

    /**
     * Finds a product by its code.
     *
     * @param code the product code
     *
     * @return the matching ProductDto
     *
     * @throws ServiceException if the product is not found
     */
    @Override
    public ProductDto findByCode(String code) throws ServiceException {
        return checkIfProductExist(code);
    }

    /**
     * Finds all products in the system.
     *
     * @return a list of ProductDto
     */
    @Override
    public List<ProductDto> findAll() {
        return repository.findAll();
    }

    /**
     * Updates an existing product.
     *
     * @param dto the product DTO with updated data
     *
     * @throws ServiceException if the product is not found or another error occurs
     */
    @Override
    public void update(ProductDto dto) throws ServiceException {
        ProductDto oldDto = checkIfProductExist(dto.getProductCode());

        copyNonNullProperties(oldDto, dto);
        dto.setTmsUpdate(LocalDateTime.now());
        repository.save(dto);
    }

    /**
     * Deletes a product by its code.
     *
     * @param code the product code to delete
     *
     * @throws ServiceException if the product is not found or another error occurs
     */
    @Override
    public void deleteByCode(String code) throws ServiceException {
        try {
            repository.deleteById(checkIfProductExist(code).getId());
        } catch (ServiceException e) {
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    /**
     * Updates the storage status of products based on product orders.
     *
     * @param productOrderDto the list of product order entities
     */
    @Override
    @Transactional
    public void updateProductStorageStatus(List<ProductOrderDto> productOrderDto) {
        List<String> productCode = productOrderDto.stream().map(ProductOrderDto::getProductCode).toList();
        List<ProductDto> entities = repository.findAllByProductCodeIn(productCode);
        LocalDateTime now = LocalDateTime.now();

        Map<String, ProductOrderDto> orderMap = productOrderDto.stream()
                .collect(Collectors.toMap(ProductOrderDto::getProductCode, Function.identity()));

        entities.forEach(dto -> {
            ProductOrderDto order = orderMap.get(dto.getProductCode());
            if (nonNull(order)) {
                dto.setSupply(dto.getSupply().subtract(order.getQuantity()));
                dto.setTmsUpdate(now);
            }
        });
    }

    /**
     * Checks which products in the order exceed available supply.
     *
     * @param productOrderDto the list of product order DTOs
     *
     * @return a list of product codes that exceed supply
     */
    @Override
    public List<String> checkRemainingSupplyProduct(List<ProductOrderDto> productOrderDto) {
        List<String> productCode = productOrderDto.stream().map(ProductOrderDto::getProductCode).toList();
        List<ProductDto> entities = repository.findAllByProductCodeIn(productCode);

        Map<String, ProductOrderDto> orderMap = productOrderDto.stream()
                .collect(Collectors.toMap(ProductOrderDto::getProductCode, Function.identity()));

        return entities.stream()
                .filter(dto -> {
                    ProductOrderDto order = orderMap.get(dto.getProductCode());
                    return order.getQuantity().compareTo(dto.getSupply()) > 0;
                })
                .map(ProductDto::getProductCode)
                .toList();
    }

    /**
     * Checks if a product exists by code, throws exception if not found.
     *
     * @param code the product code
     *
     * @return the matching ProductDto
     *
     * @throws ServiceException if the product is not found
     */
    private ProductDto checkIfProductExist(String code) throws ServiceException {
        ProductDto dto = repository.findByProductCodeIgnoreCase(code);
        if (isNull(dto))
            throw new ServiceException(PRODUCT_NOT_FOUND, "Product with code: " + code + " not found");
        return dto;
    }

}
