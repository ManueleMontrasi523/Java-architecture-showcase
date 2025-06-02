package it.marketplace.microservices.common.dto;

import it.marketplace.microservices.common.enums.RoleEnum;
import it.marketplace.microservices.common.enums.StatusUserEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a user in the marketplace system.
 * Contains user details such as name, email, address, status, role, and subscription date.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String residenceAddress;
    private String residenceCity;

    private StatusUserEnum status;
    private RoleEnum role;
    private LocalDateTime tmsSubscriptionDate;

}
