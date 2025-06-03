package it.marketplace.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.common.dto.PaymentOrderDto;
import it.marketplace.entity.PaymentOrderEntity;

/**
 * Mapper class for converting between PaymentOrder DTOs, resources, and entities in the marketplace system.
 * Uses Jackson ObjectMapper for object conversion.
 */
public class PaymentOrderMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Converts a PaymentOrderEntity to a PaymentOrderDto.
     *
     * @param entity the PaymentOrderEntity to convert
     *
     * @return the corresponding PaymentOrderDto
     */
    public static PaymentOrderDto toDto(PaymentOrderEntity entity) {
        if (entity == null) return null;
        return mapper.convertValue(entity, PaymentOrderDto.class);
    }

    /**
     * Converts a PaymentOrderDto to a PaymentOrderEntity.
     *
     * @param dto the PaymentOrderDto to convert
     *
     * @return the corresponding PaymentOrderEntity
     */
    public static PaymentOrderEntity toEntity(PaymentOrderDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, PaymentOrderEntity.class);
    }

}
