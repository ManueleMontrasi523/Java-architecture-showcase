package it.marketplace.microservices.database.entity;

import it.marketplace.microservices.common.enums.CategoryEnum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_sequence", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_CODE", unique = true)
    private String productCode;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private double PRICE;

    @Column(name = "SUPPLY")
    private String supply;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private CategoryEnum category;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "TMS_UPDATE")
    private LocalDateTime tmsUpdate;

}
