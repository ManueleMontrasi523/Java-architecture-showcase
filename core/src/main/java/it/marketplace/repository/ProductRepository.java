package it.marketplace.repository;

import it.marketplace.common.dto.ProductDto;

import java.util.List;

public interface ProductRepository {

    void save(ProductDto dto);

    void saveAll(List<ProductDto> dto);

    List<ProductDto> findAll();

    /**
     * Finds a product by its code, ignoring case.
     *
     * @param productCode the product code
     *
     * @return the matching ProductDto, or null if not found
     */
    ProductDto findByProductCodeIgnoreCase(String productCode);

    /**
     * Finds all products by a list of product codes.
     *
     * @param productCode the list of product codes
     *
     * @return a list of ProductDto
     */
    List<ProductDto> findAllByProductCodeIn(List<String> productCode);

    void deleteById(Long id);
}
