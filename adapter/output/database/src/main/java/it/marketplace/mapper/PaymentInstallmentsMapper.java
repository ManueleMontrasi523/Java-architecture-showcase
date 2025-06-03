package it.marketplace.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.common.dto.PaymentInstallmentsDto;
import it.marketplace.entity.PaymentInstallmentsEntity;

/**
 * Mapper class for converting between PaymentInstallments DTOs, resources, and entities in the marketplace system.
 * Uses Jackson ObjectMapper for object conversion.
 */
public class PaymentInstallmentsMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Converts a PaymentInstallmentsEntity to a PaymentInstallmentsDto.
     *
     * @param entity the PaymentInstallmentsEntity to convert
     *
     * @return the corresponding PaymentInstallmentsDto
     */
    public static PaymentInstallmentsDto toDto(PaymentInstallmentsEntity entity) {
        if (entity == null) return null;
        return mapper.convertValue(entity, PaymentInstallmentsDto.class);
    }

    /**
     * Converts a PaymentInstallmentsDto to a PaymentInstallmentsEntity.
     *
     * @param dto the PaymentInstallmentsDto to convert
     *
     * @return the corresponding PaymentInstallmentsEntity
     */
    public static PaymentInstallmentsEntity toEntity(PaymentInstallmentsDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, PaymentInstallmentsEntity.class);
    }

}
