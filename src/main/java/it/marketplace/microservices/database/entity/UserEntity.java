package it.marketplace.microservices.database.entity;

import it.marketplace.microservices.common.enums.RoleEnum;
import it.marketplace.microservices.common.enums.StatusUserEnum;
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
@Table(name = "USER")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "RESIDENCE_ADDRESS")
    private String residenceAddress;

    @Column(name = "RESIDENCE_CITY")
    private String residenceCity;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private RoleEnum role;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusUserEnum status;

    @Column(name = "TMS_SUBSCRIPTION_DATE")
    private LocalDateTime tmsSubscriptionDate;

    @Column(name = "TMS_UPDATE")
    private LocalDateTime tmsUpdate;

}
