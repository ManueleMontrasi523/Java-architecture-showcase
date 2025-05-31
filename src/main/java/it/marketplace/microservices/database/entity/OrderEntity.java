package it.marketplace.microservices.database.entity;

import it.marketplace.microservices.common.enums.StatusOrderEnum;

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
@Table(name = "ORDERS")
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_sequence", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORDER_CODE", unique = true)
    private String orderCode;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity name;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusOrderEnum status;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "UNIT_PRICE")
    private Double unitPrice;

    @Column(name = "TOTAL")
    private Double total;

    @Column(name = "ORDER_DATE")
    private LocalDateTime orderDate;

    @Column(name = "TMS_UPDATE")
    private LocalDateTime tmsUpdate;

}
