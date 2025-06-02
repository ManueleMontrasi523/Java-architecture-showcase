package it.marketplace.microservices.database.entity;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing an order in the marketplace system.
 * Contains order details, user, product list, status, rejection reason, and relevant dates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_sequence", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "ORDER_CODE", unique = true, nullable = false)
    private String orderCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_USER", referencedColumnName = "ID", nullable = false)
    private UserEntity user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_ORDER", referencedColumnName = "ID", nullable = false)
    private List<ProductOrderEntity> productOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private StatusOrderEnum status;

    @Column(name = "REJECT_REASON")
    private String rejectReason;

    @DateTimeFormat
    @Column(name = "ORDER_DATE", nullable = false)
    private LocalDateTime orderDate;

    @DateTimeFormat
    @Column(name = "TMS_UPDATE", nullable = false)
    private LocalDateTime tmsUpdate;

}
