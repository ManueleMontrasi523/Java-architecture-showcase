package it.marketplace.microservices.database.repository;

import it.marketplace.microservices.database.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    ProductEntity findByProductCodeIgnoreCase(String productCode);

}
