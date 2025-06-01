package it.marketplace.microservices.common.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import it.marketplace.microservices.common.enums.StatusOrderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResource {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String orderCode;
    private UserResource userResource;
    private List<ProductOrderResource> productResource;
    private StatusOrderEnum status;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime orderDate;

}
