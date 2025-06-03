package it.marketplace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a product order in the marketplace system.
 * Contains details about the ordered product, quantity, pricing, and relevant dates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT_ORDER")
public class ProductOrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_order_seq")
    @SequenceGenerator(name = "product_order_seq", sequenceName = "product_order_sequence", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "ORDER_CODE", nullable = false)
    private String orderCode;

    @Column(name = "PRODUCT_CODE", nullable = false)
    private String productCode;

    @Column(name = "QUANTITY", nullable = false)
    private BigDecimal quantity;

    @Column(name = "UNIT_PRICE", nullable = false)
    private Double unitPrice;

    @Column(name = "TOTAL", nullable = false)
    private Double total;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "TMS_UPDATE")
    private LocalDateTime tmsUpdate;

}
