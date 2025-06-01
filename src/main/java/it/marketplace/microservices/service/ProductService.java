package it.marketplace.microservices.service;

import it.marketplace.microservices.common.dto.ProductDto;
import it.marketplace.microservices.common.dto.ProductOrderDto;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.database.entity.ProductOrderEntity;

import java.util.List;

public interface ProductService {

    void save(ProductDto dto) throws ServiceException;

    ProductDto findByCode(String code) throws ServiceException;

    List<ProductDto> findAll() throws ServiceException;

    void update(ProductDto dto) throws ServiceException;

    void deleteByCode(String code) throws ServiceException;

    void updateProductStorageStatus(List<ProductOrderEntity> productOrderEntity);

    List<String> checkRemainingSupplyProduct(List<ProductOrderDto> productOrderEntity);
}
