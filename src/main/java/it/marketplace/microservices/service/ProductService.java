package it.marketplace.microservices.service;

import it.marketplace.microservices.common.dto.ProductDto;
import it.marketplace.microservices.common.dto.ProductOrderDto;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.database.entity.ProductOrderEntity;

import java.util.List;

/**
 * Service interface for managing products in the marketplace system.
 * Provides methods for product creation, update, deletion, inventory management, and supply checks.
 */
public interface ProductService {

    /**
     * Saves a new product.
     * @param dto the product DTO to save
     * @throws ServiceException if the product already exists or another error occurs
     */
    void save(ProductDto dto) throws ServiceException;

    /**
     * Saves a list of products.
     * @param dto the list of product DTOs to save
     */
    void saveAll(List<ProductDto> dto);

    /**
     * Saves a list of products directly without additional processing.
     * @param dto the list of product DTOs to save
     */
    void saveAllDirectly(List<ProductDto> dto);

    /**
     * Finds a product by its code.
     * @param code the product code
     * @return the matching ProductDto
     * @throws ServiceException if the product is not found
     */
    ProductDto findByCode(String code) throws ServiceException;

    /**
     * Finds all products in the system.
     * @return a list of ProductDto
     * @throws ServiceException if an error occurs
     */
    List<ProductDto> findAll() throws ServiceException;

    /**
     * Updates an existing product.
     * @param dto the product DTO with updated data
     * @throws ServiceException if the product is not found or another error occurs
     */
    void update(ProductDto dto) throws ServiceException;

    /**
     * Deletes a product by its code.
     * @param code the product code to delete
     * @throws ServiceException if the product is not found or another error occurs
     */
    void deleteByCode(String code) throws ServiceException;

    /**
     * Updates the storage status of products based on product orders.
     * @param productOrderEntity the list of product order entities
     */
    void updateProductStorageStatus(List<ProductOrderEntity> productOrderEntity);

    /**
     * Checks which products in the order exceed available supply.
     * @param productOrderEntity the list of product order DTOs
     * @return a list of product codes that exceed supply
     */
    List<String> checkRemainingSupplyProduct(List<ProductOrderDto> productOrderEntity);

}
