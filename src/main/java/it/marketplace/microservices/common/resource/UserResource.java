package it.marketplace.microservices.common.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import it.marketplace.microservices.common.enums.RoleEnum;
import it.marketplace.microservices.common.enums.StatusUserEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Resource class representing a user in the marketplace system for API responses.
 * Contains user details such as name, email, address, status, role, and subscription date.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResource {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String name;
    private String lastname;
    private String email;
    private String residenceAddress;
    private String residenceCity;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private StatusUserEnum status;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private RoleEnum role;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime tmsSubscriptionDate;

}
