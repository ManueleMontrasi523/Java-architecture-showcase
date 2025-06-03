package it.marketplace.common.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.common.dto.PaymentInstallmentsDto;
import it.marketplace.common.resource.PaymentInstallmentsResource;

/**
 * Mapper class for converting between PaymentInstallments DTOs, resources, and entities in the marketplace system.
 * Uses Jackson ObjectMapper for object conversion.
 */
public class PaymentInstallmentsMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Converts a PaymentInstallmentsDto to a PaymentInstallmentsResource.
     *
     * @param dto the PaymentInstallmentsDto to convert
     *
     * @return the corresponding PaymentInstallmentsResource
     */
    public static PaymentInstallmentsResource toResource(PaymentInstallmentsDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, PaymentInstallmentsResource.class);
    }

    /**
     * Converts a PaymentInstallmentsResource to a PaymentInstallmentsDto.
     *
     * @param resource the PaymentInstallmentsResource to convert
     *
     * @return the corresponding PaymentInstallmentsDto
     */
    public static PaymentInstallmentsDto toDto(PaymentInstallmentsResource resource) {
        if (resource == null) return null;
        return mapper.convertValue(resource, PaymentInstallmentsDto.class);
    }

}
