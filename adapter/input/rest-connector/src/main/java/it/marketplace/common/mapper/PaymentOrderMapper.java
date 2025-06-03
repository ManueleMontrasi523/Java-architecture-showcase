package it.marketplace.common.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.common.dto.PaymentOrderDto;
import it.marketplace.common.resource.PaymentOrderResource;

/**
 * Mapper class for converting between PaymentOrder DTOs, resources, and entities in the marketplace system.
 * Uses Jackson ObjectMapper for object conversion.
 */
public class PaymentOrderMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Converts a PaymentOrderDto to a PaymentOrderResource.
     *
     * @param dto the PaymentOrderDto to convert
     *
     * @return the corresponding PaymentOrderResource
     */
    public static PaymentOrderResource toResource(PaymentOrderDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, PaymentOrderResource.class);
    }

    /**
     * Converts a PaymentOrderResource to a PaymentOrderDto.
     *
     * @param resource the PaymentOrderResource to convert
     *
     * @return the corresponding PaymentOrderDto
     */
    public static PaymentOrderDto toDto(PaymentOrderResource resource) {
        if (resource == null) return null;
        return mapper.convertValue(resource, PaymentOrderDto.class);
    }
}
