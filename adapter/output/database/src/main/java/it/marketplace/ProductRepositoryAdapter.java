package it.marketplace;

import it.marketplace.common.dto.ProductDto;
import it.marketplace.repository.ProductJpaRepository;
import it.marketplace.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static it.marketplace.mapper.ProductMapper.toDto;
import static it.marketplace.mapper.ProductMapper.toEntity;

/**
 * Repository interface for managing ProductEntity persistence operations.
 * Provides methods to find products by code and by a list of codes.
 */
@Repository
public class ProductRepositoryAdapter implements ProductRepository {

    @Autowired
    private ProductJpaRepository repository;

    /**
     * Saves a product in the database.
     *
     * @param dto the product to save
     */
    @Override
    public void save(ProductDto dto) {
        repository.save(toEntity(dto));
    }

    /**
     * Saves a list of products in the database.
     *
     * @param dto the list of products to save
     */
    @Override
    public void saveAll(List<ProductDto> dto) {
        repository.saveAll(dto.stream().map(it.marketplace.mapper.ProductMapper::toEntity).toList());
    }

    /**
     * Returns all products present in the database.
     *
     * @return list of ProductDto
     */
    @Override
    public List<ProductDto> findAll() {
        return repository.findAll().stream()
                .map(it.marketplace.mapper.ProductMapper::toDto)
                .toList();
    }

    /**
     * Finds a product by its code, ignoring case.
     *
     * @param productCode the product code
     *
     * @return the matching ProductEntity, or null if not found
     */
    @Override
    public ProductDto findByProductCodeIgnoreCase(String productCode) {
        return toDto(repository.findByProductCodeIgnoreCase(productCode));
    }

    /**
     * Finds all products by a list of product codes.
     *
     * @param productCode the list of product codes
     *
     * @return a list of ProductEntity
     */
    @Override
    public List<ProductDto> findAllByProductCodeIn(List<String> productCode) {
        return repository.findAllByProductCodeIn(productCode).stream()
                .map(it.marketplace.mapper.ProductMapper::toDto)
                .toList();
    }

    /**
     * Deletes a product by its id.
     *
     * @param id the id of the product to delete
     */
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
