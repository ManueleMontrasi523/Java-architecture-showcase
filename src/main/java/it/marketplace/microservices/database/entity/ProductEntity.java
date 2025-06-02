package it.marketplace.microservices.database.entity;

import it.marketplace.microservices.common.enums.CategoryEnum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a product in the marketplace system.
 * Contains product details such as code, name, description, price, supply, category, and relevant dates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_sequence", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "PRODUCT_CODE", unique = true, nullable = false)
    private String productCode;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE", nullable = false)
    private double PRICE;

    @Column(name = "SUPPLY", nullable = false)
    private BigDecimal supply;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY", nullable = false)
    private CategoryEnum category;

    @DateTimeFormat
    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @DateTimeFormat
    @Column(name = "TMS_UPDATE", nullable = false)
    private LocalDateTime tmsUpdate;

}
