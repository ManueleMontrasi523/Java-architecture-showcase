package it.marketplace.microservices.database.entity;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity representing a payment order in the marketplace system.
 * Contains details for simulating completed payments, including order code, status, debit, and relevant dates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAYMENT_ORDER")
public class PaymentOrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_order_seq")
    @SequenceGenerator(name = "payment_order_seq", sequenceName = "payment_order_sequence", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "ORDER_CODE", unique = true, nullable = false)
    private String orderCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private StatusOrderEnum status;

    @Column(name = "DEBIT", nullable = false)
    private Double debit;

    @DateTimeFormat
    @Column(name = "ORDER_DATE", nullable = false)
    private LocalDateTime orderDate;

    @DateTimeFormat
    @Column(name = "TMS_UPDATE", nullable = false)
    private LocalDateTime tmsUpdate;

}
