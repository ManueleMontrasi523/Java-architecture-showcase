package it.marketplace.microservices.common.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import it.marketplace.microservices.common.enums.RoleEnum;
import it.marketplace.microservices.common.enums.StatusUserEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResource {

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
