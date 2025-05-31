package it.marketplace.microservices.service;

import it.marketplace.microservices.common.dto.ProductDto;
import it.marketplace.microservices.common.exception.ProductServiceException;

import java.util.List;

public interface ProductService {

    void save(ProductDto dto) throws ProductServiceException;

    ProductDto findByCode(String code) throws ProductServiceException;

    List<ProductDto> findAll() throws ProductServiceException;

    void update(ProductDto dto) throws ProductServiceException;

    void deleteByCode(String code) throws ProductServiceException;

}
