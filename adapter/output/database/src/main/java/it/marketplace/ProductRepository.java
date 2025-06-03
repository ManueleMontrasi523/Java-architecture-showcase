package it.marketplace;

import it.marketplace.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing ProductEntity persistence operations.
 * Provides methods to find products by code and by a list of codes.
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    /**
     * Finds a product by its code, ignoring case.
     * @param productCode the product code
     * @return the matching ProductEntity, or null if not found
     */
    ProductEntity findByProductCodeIgnoreCase(String productCode);

    /**
     * Finds all products by a list of product codes.
     * @param productCode the list of product codes
     * @return a list of ProductEntity
     */
    List<ProductEntity> findAllByProductCodeIn(List<String> productCode);
}
