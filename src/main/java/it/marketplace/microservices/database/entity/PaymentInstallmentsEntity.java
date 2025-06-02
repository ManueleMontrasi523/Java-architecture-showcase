package it.marketplace.microservices.database.entity;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * THIS TABLE WILL BE USED TO SIMULATE CORRECTLY TERMINATED PAYMENTS WITH 12 INSTALLMENTS
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAYMENT_INSTALLMENTS")
public class PaymentInstallmentsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_installments_seq")
    @SequenceGenerator(name = "payment_installments_seq", sequenceName = "payment_installments_sequence", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "REFERENCE", nullable = false)
    private String reference;

    @Column(name = "ORDER_CODE", nullable = false)
    private String orderCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private StatusOrderEnum status;

    @Column(name = "DEBIT", nullable = false)
    private Double debit;

    @DateTimeFormat
    @Column(name = "TMS_UPDATE", nullable = false)
    private LocalDateTime tmsUpdate;

}
